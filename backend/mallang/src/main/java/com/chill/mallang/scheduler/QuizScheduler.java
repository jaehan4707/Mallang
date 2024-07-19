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
//    @Scheduled(fixedRate = 10000)
//    public void generateQuiz(){
//        quizService.createQuizFromPrompt();
//        logger.info("Generate Quiz");
//    }

}
