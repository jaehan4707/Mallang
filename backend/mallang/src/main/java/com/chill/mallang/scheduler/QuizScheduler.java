package com.chill.mallang.scheduler;

import com.chill.mallang.domain.quiz.service.core.OpenAIService;
import com.chill.mallang.domain.quiz.service.core.QuizAssignmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QuizScheduler {

    private static final Logger logger = LoggerFactory.getLogger(QuizScheduler.class);
    private final QuizAssignmentService quizAssignmentService;

    // 문제 생성 스케쥴러
//    @Scheduled(fixedRate = 100_000)
//    public void generateQuiz(){
//        logger.info("TEST!!!! Start generate quiz");
//        openAIService.makeNewQuestion();
//        logger.info("TEST!!!! Generate Quiz");
//    }

    // Test용 30분 스케쥴링
//    @Scheduled(fixedRate = 1_800_000)
    // 실 사용 6시간 스케쥴링
    // @Scheduled(cron = "0 0 0,6,12,18 * * *")
    // @Transactional
    // public void assignQuizzesToArea(){
    //     logger.info("퀴즈 재배치 시작");
    //     quizAssignmentService.assignQuizzesToAreas();
    //     logger.info("퀴즈 재배치 완료");
    // }
}
