package com.chill.mallang.domain.quiz.service;

import com.chill.mallang.domain.quiz.dto.request.RequestQuizAnswer;
import com.chill.mallang.domain.quiz.dto.request.RequestQuizResult;
import com.chill.mallang.domain.quiz.dto.response.ResponseQuiz;
import com.chill.mallang.domain.quiz.error.QuizErrorCode;
import com.chill.mallang.domain.quiz.model.Answer;
import com.chill.mallang.domain.quiz.model.Quiz;
import com.chill.mallang.domain.quiz.repository.AnswerRepository;
import com.chill.mallang.domain.quiz.repository.QuizRepository;
import com.chill.mallang.domain.quiz.repository.TotalScoreRepository;
import com.chill.mallang.domain.quiz.service.core.CoreService;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.errors.exception.RestApiException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.chill.mallang.util.ValidationUtils.requireNonNull;

@Slf4j
@Service
public class QuizService {
    private final Logger logger = LoggerFactory.getLogger(QuizService.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GPTService gptService;
    @Autowired
    private CoreService coreService;

    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final TotalScoreRepository totalScoreRepository;

    public QuizService( QuizRepository quizRepository, UserRepository userRepository,
                        AnswerRepository answerRepository, TotalScoreRepository totalScoreRepository,
                        EntityManager entityManager ) {
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
        this.answerRepository = answerRepository;
        this.totalScoreRepository = totalScoreRepository;
        this.entityManager = entityManager;
    }


    public Map<String, Object> getById(Long quizID) {
        Optional<Quiz> quiz = quizRepository.findById(quizID);
        logger.info(String.valueOf("QuizDTO : "), quiz);

        if (quiz.isPresent()) {
            ResponseQuiz responseQuiz = ResponseQuiz.builder()
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

    public void submitAnswer(RequestQuizAnswer requestQuizAnswer){
        logger.info(String.valueOf(requestQuizAnswer));

        String question = quizRepository.findById(requestQuizAnswer.getQuizId()).get().getQuestion();
        String answer = requestQuizAnswer.getUserAnswer();

        System.out.println(answer);

        float score = gptService.getScore(question, answer);

        saveAnswer(requestQuizAnswer, score);

        logger.info("Success Save Answer");
    }

    public Answer saveAnswer(RequestQuizAnswer requestQuizAnswer, float score){

        Answer answer = Answer.builder()
                .user(userRepository.findById(requestQuizAnswer.getUserId()).orElseThrow(() -> new RestApiException(QuizErrorCode.USER_NOT_FOUND)))
                .quiz(quizRepository.findById(requestQuizAnswer.getQuizId()).orElseThrow(() -> new RestApiException(QuizErrorCode.QUIZ_NOT_FOUND)))
                .answer(requestQuizAnswer.getUserAnswer())
                .answerTime(requestQuizAnswer.getAnswerTime())
                .score(score)
                .check_fin(0) // 기본값 설정
                .build();

        logger.info("Add answer data : " , answer.toString());
        return answerRepository.save(answer);
    }

    @Transactional
    public Map<String, Object> getAreaQuiz(Long areaID){
        requireNonNull(areaID, QuizErrorCode.AREA_ID_NULL);
        Map<String, Object> response = new HashMap<>();
        response.put("data",quizRepository.getQuizByArea(areaID));
        return response;
    }

    @Transactional
    public Map<String, Object> quizResult(RequestQuizResult requestQuizResult){
        Long userID = requireNonNull(requestQuizResult.getUserID(), QuizErrorCode.USER_ID_NULL);
        Long areaID = requireNonNull(requestQuizResult.getAreaID(), QuizErrorCode.AREA_ID_NULL);
        Long factionID = requireNonNull(requestQuizResult.getFactionID(), QuizErrorCode.FACTION_ID_NULL);

        Map<String, Object> user = new HashMap<>();
        Map<String, Object> team = new HashMap<>();

        // 최종 제출 세팅
        Long[] idx = requestQuizResult.getQuizID();
        List<Float> responseScore = new ArrayList<>();

        // User Score 확인
        float sum = 0;
        int roundNum = 0;
        for(Long quizID : idx){
            answerRepository.setAnswerTrue(userID, quizID);
            logger.info(quizID + "번 Answer 최종 제출 완료");
            // Float nowScore =answerRepository.findTop1AnswerScore(quizID);
            Float nowScore =answerRepository.findTotalScoreByAreaID(quizID);
            sum += nowScore;
            responseScore.add(nowScore);
            roundNum++;
        }
        user.put("Score", responseScore);
        user.put("Total Score", sum);
        logger.info("라운드 최종 점수 저장 시작");

        coreService.storeTotalScore(userID, areaID, sum, factionID);

        Map<String, Object> response = new HashMap<>();
        List<Float> teamScoreList = totalScoreRepository.findTotalScoreByAreaID(areaID);

        if (teamScoreList.size() > 0) {
            team.put("My Team Total Score", teamScoreList.get(0));
        }
        if (teamScoreList.size() > 1) {
            team.put("Oppo Team Total Score", teamScoreList.get(1));
        }
        team.put("My Team Rank", totalScoreRepository.findTop3(areaID, factionID));

        Map<String, Object> data = new HashMap<>();

        data.put("User", user);
        data.put("Team", team);
        response.put("data", data);
        return response;
    }



}
