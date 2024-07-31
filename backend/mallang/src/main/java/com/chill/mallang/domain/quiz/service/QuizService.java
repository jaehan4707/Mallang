package com.chill.mallang.domain.quiz.service;

import com.chill.mallang.domain.quiz.dto.RequestQuizAnswer;
import com.chill.mallang.domain.quiz.dto.ResponseQuiz;
import com.chill.mallang.domain.quiz.error.QuizErrorCode;
import com.chill.mallang.domain.quiz.model.Answer;
import com.chill.mallang.domain.quiz.model.Quiz;
import com.chill.mallang.domain.quiz.repository.AnswerRepository;
import com.chill.mallang.domain.quiz.repository.QuizRepository;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.errors.exception.RestApiException;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class QuizService {


    private Logger logger = LoggerFactory.getLogger(QuizService.class);
    @Autowired
    private GPTService gptService;

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnswerRepository answerRepository;

    public Map<String, Object> getById(Long quizID) {
        Optional<Quiz> quiz = quizRepository.findById(quizID);
        logger.info(String.valueOf("QuizDTO : "), quiz);

        if (quiz.isPresent()) {
            ResponseQuiz responseQuiz = ResponseQuiz.builder()
                    .id(quiz.get().getId())
                    .question(quiz.get().getQuestion())  // 추가된 부분
                    .answer(quiz.get().getAnswer())
                    .difficulty(quiz.get().getDifficulty())
                    .type(quiz.get().getType())
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

        double score = gptService.getScore(question, answer);

        saveAnswer(requestQuizAnswer, score);

        logger.info("Success Save Answer");
    }

    public Answer saveAnswer(RequestQuizAnswer requestQuizAnswer, double score){

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
}
