package com.chill.mallang.scheduler;

import com.chill.mallang.domain.quiz.model.Quiz;
import com.chill.mallang.domain.quiz.repository.QuizRepository;
import com.chill.mallang.domain.quiz.service.QuizService;
import com.chill.mallang.domain.quiz.service.core.QuizAssignmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class QuizScheduler {

    private static final Logger logger = LoggerFactory.getLogger(QuizScheduler.class);
    private final QuizService quizService;
    private final QuizRepository quizRepository;
    private final QuizAssignmentService quizAssignmentService;

    @Autowired
    public QuizScheduler(QuizService quizService, QuizRepository quizRepository, QuizAssignmentService quizAssignmentService) {
        this.quizService = quizService;
        this.quizRepository = quizRepository;
        this.quizAssignmentService = quizAssignmentService;
    }

    // 문제 생성 스케쥴러
//    @Scheduled(fixedRate = 100_000)
//    public void generateQuiz(){
//        logger.info("TEST!!!! Start generate quiz");
////        quizService.createQuizFromPrompt();
//        logger.info("TEST!!!! Generate Quiz");
//    }

    @Scheduled(fixedRate = 1_800_000)
    @Transactional
    public void assignQuizzesToArea(){
        logger.info("퀴즈 재배치 시작");
        quizAssignmentService.assignQuizzesToAreas();
        logger.info("퀴즈 재배치 완료");

    }
}
