package com.chill.mallang.domain.quiz.service;

import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.quiz.dto.request.QuizAnswerRequest;
import com.chill.mallang.domain.quiz.dto.request.QuizResultRequest;
import com.chill.mallang.domain.quiz.dto.response.QuizResponse;
import com.chill.mallang.domain.quiz.dto.response.TeamRankResponse;
import com.chill.mallang.domain.quiz.error.QuizErrorCode;
import com.chill.mallang.domain.quiz.model.Answer;
import com.chill.mallang.domain.quiz.model.Quiz;
import com.chill.mallang.domain.quiz.repository.AnswerRepository;
import com.chill.mallang.domain.quiz.repository.QuizRepository;
import com.chill.mallang.domain.quiz.repository.TotalScoreRepository;
import com.chill.mallang.domain.quiz.service.core.CoreService;
import com.chill.mallang.domain.quiz.service.core.OpenAIService;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.domain.user.service.impl.UserServiceImpl;
import com.chill.mallang.errors.exception.RestApiException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.chill.mallang.util.ValidationUtils.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuizService {
    private final Logger logger = LoggerFactory.getLogger(QuizService.class);

    private final CoreService coreService;
    private final OpenAIService openAIService;

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;
    private final AnswerRepository answerRepository;
    private final TotalScoreRepository totalScoreRepository;
    private final UserServiceImpl userServiceImpl;


    public Map<String, Object> getById(Long quizID) {
        Optional<Quiz> quiz = quizRepository.findById(quizID);
        logger.info(String.valueOf("QuizDTO : "), quiz);

        if (quiz.isPresent()) {
            QuizResponse responseQuiz = QuizResponse.builder()
                    .id(quiz.get().getId())
                    .question(quiz.get().getQuestion())  // 추가된 부분
                    .answer(quiz.get().getAnswer())
                    .difficulty(quiz.get().getDifficulty())
                    .build();

            return new HashMap<String, Object>() {{
                put("data", responseQuiz);
            }};

        } else {
            throw new RestApiException(QuizErrorCode.INVALID_QUIZ_PK);
        }
    }

    public void submitAnswer(QuizAnswerRequest requestQuizAnswer) {
        logger.info(String.valueOf(requestQuizAnswer));

        String question = quizRepository.findById(requestQuizAnswer.getQuizId()).get().getQuestion();
        String answer = requestQuizAnswer.getUserAnswer();

        float score = Float.parseFloat(openAIService.getScoreUseAI(question, answer));
        logger.info("새로운 GPT의 점수 : " + String.valueOf(openAIService.getScoreUseAI(question, answer)));
        saveAnswer(requestQuizAnswer, score);

        logger.info("Success Save Answer");
    }

    public void saveAnswer(QuizAnswerRequest requestQuizAnswer, float score) {

        Answer answer = Answer.builder()
                .user(userRepository.findById(requestQuizAnswer.getUserId()).orElseThrow(() -> new RestApiException(QuizErrorCode.USER_NOT_FOUND)))
                .quiz(quizRepository.findById(requestQuizAnswer.getQuizId()).orElseThrow(() -> new RestApiException(QuizErrorCode.QUIZ_NOT_FOUND)))
                .area(areaRepository.findById(requestQuizAnswer.getAreaId()).orElseThrow(() -> new RestApiException(QuizErrorCode.AREA_NOT_FOUND)))
                .answer(requestQuizAnswer.getUserAnswer())
                .answerTime(requestQuizAnswer.getAnswerTime())
                .score(score)
                .check_fin(0) // 기본값 설정
                .build();

        logger.info("Add answer data : ", answer.toString());
        answerRepository.save(answer);
    }

    @Transactional
    public Map<String, Object> getAreaQuiz(Long areaID, Long userID) {
        userRepository.minusTryCount(userID);
        requireNonNull(areaID, QuizErrorCode.AREA_ID_NULL);
        Map<String, Object> response = new HashMap<>();
        response.put("data", quizRepository.getQuizByArea(areaID));
        return response;
    }

    @Transactional
    public Map<String, Object> quizResult(QuizResultRequest requestQuizResult) {
        Long userID = requestQuizResult.getUserID();
        Long areaID = requestQuizResult.getAreaID();
        Long factionID = requestQuizResult.getFactionID();

        Map<String, Object> user = new HashMap<>();
        Map<String, Object> team = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        // 최종 제출 세팅

        List<Float> responseScore = new ArrayList<>();
        // User Score 확인
        float sum = 0;
        for (Long quizID : requestQuizResult.getQuizID()) {
            answerRepository.setAnswerTrue(userID, quizID);
            logger.info(quizID + "번 Answer 최종 제출 완료");
            Float nowScore = answerRepository.findTop1AnswerScore(quizID);
            sum += nowScore;
            logger.info(String.valueOf(nowScore));
            responseScore.add(nowScore);
            logger.info("값 저장 성공");
        }

        user.put("Score", responseScore);
        user.put("Total Score", sum);


        logger.info("라운드 최종 점수 저장 시작");

        coreService.storeTotalScore(userID, areaID, sum, factionID);
        userServiceImpl.addExp(userID, sum);

        List<Float> teamScoreList = totalScoreRepository.findTotalScoreByAreaID(areaID);
        team.put("My Team Total Score", teamScoreList.get(0));

        if (teamScoreList.size() == 1) {
            team.put("Oppo Team Total Score", 0.0);
        } else {
            team.put("Oppo Team Total Score", teamScoreList.get(1));
        }
        team.put("My Team Rank", teamRankTop3(areaID, factionID));

        Map<String, Object> data = new HashMap<>();
        data.put("User", user);
        data.put("Team", team);

        response.put("data", data);
        return response;
    }

    public List<Map<String, Object>> teamRankTop3(Long areaID, Long factionID) {
        List<Map<String, Object>> teamRankList = new ArrayList<>();
        List<TeamRankResponse> top3Ranks = totalScoreRepository.findTop3Results(areaID, factionID);

        for (TeamRankResponse rankResponse : top3Ranks) {
            Map<String, Object> rankMap = new HashMap<>();
            rankMap.put("name", rankResponse.getNickName());
            rankMap.put("score", rankResponse.getTotalScore());
            teamRankList.add(rankMap);
        }
        return teamRankList;
    }


}
