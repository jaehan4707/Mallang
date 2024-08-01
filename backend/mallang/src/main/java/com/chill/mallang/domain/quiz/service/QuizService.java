package com.chill.mallang.domain.quiz.service;

import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.quiz.dto.request.RequestQuizAnswer;
import com.chill.mallang.domain.quiz.dto.request.RequestQuizResult;
import com.chill.mallang.domain.quiz.dto.response.ResponseQuiz;
import com.chill.mallang.domain.quiz.error.QuizErrorCode;
import com.chill.mallang.domain.quiz.model.Answer;
import com.chill.mallang.domain.quiz.model.Quiz;
import com.chill.mallang.domain.quiz.model.TotalScore;
import com.chill.mallang.domain.quiz.repository.AnswerRepository;
import com.chill.mallang.domain.quiz.repository.QuizRepository;
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

@Slf4j
@Service
public class QuizService {
    private Logger logger = LoggerFactory.getLogger(QuizService.class);
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private GPTService gptService;
    @Autowired
    private CoreService coreService;

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private AreaRepository areaRepository;

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
                    put("Data", responseQuiz);
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
        Map<String, Object> response = new HashMap<>();
        response.put("data",quizRepository.getQuizByArea(areaID));
        return response;
    }

    @Transactional
    public void quizResult(RequestQuizResult requestQuizResult){
        Long userID = requestQuizResult.getUserID();
        Long areaID = requestQuizResult.getAreaID();

        // 최종 제출 세팅
        Long[] idx = requestQuizResult.getQuizID();
        List<Float> responseScore = new ArrayList<>();

        // User Score 확인
        float sum = 0;
        for(Long quizID : idx){
            answerRepository.setAnswerTrue(userID, quizID);
            logger.info(quizID + "번 Answer 최종 제출 완료");
            Float nowScore =answerRepository.findTop1AnswerScore(quizID);
            sum += nowScore;
            responseScore.add(nowScore);
        }

        // 라운드 최종 점수 저장
        coreService.storeTotalScore(userID, areaID, sum);
        /*---------------------------------------여기 아래 부터는 Team 영역 계산 필요-------------------------------------------*/
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(now);
//        int year = now.getYear();
//        int month = now.getMonthValue();
//        int day = now.getDayOfMonth();
//
//        logger.info("결과 조회 필요한 Quiz ID " + Arrays.toString(idx));
//        List<Object[]> result = answerRepository.getResultUser(year, month, day, idx, userID);
//        System.out.println(result.size());
//        for(Object[] row : result){
//            for(Object o : row){
//                System.out.println(o.toString());
//            }
//        }
        // Response Result User Setting

        // 1. 오늘 기준 Answer 테이블의 순서대로 긁어와서 몇번째 인지 확인하기
        // 2. 오늘 기준 Answer 테이블의 데이터에서 quizID와 동일한 최신 데이터의 점수 확인
        // 3. 해당 점수들의 합 구하기.


        Map<String, Object> response = new HashMap<>();
    }



}
