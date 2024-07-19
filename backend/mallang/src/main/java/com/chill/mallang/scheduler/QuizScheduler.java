package com.chill.mallang.scheduler;

import com.chill.mallang.domain.quiz.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QuizScheduler {

    private static final Logger logger = LoggerFactory.getLogger(QuizScheduler.class);
    private final QuizService quizService;

    @Autowired
    public QuizScheduler(QuizService quizService) {
        this.quizService = quizService;
    }


    // 문제 생성 스케쥴러
    @Scheduled(fixedRate = 100_000)
    public void generateQuiz(){
        logger.info("TEST!!!! Start generate quiz");
//        quizService.createQuizFromPrompt();
        logger.info("TEST!!!! Generate Quiz");
    }

}
