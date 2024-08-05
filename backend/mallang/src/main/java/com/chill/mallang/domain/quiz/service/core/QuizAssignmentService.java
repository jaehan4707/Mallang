package com.chill.mallang.domain.quiz.service.core;

import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.quiz.repository.QuizRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class QuizAssignmentService {
    private final QuizRepository quizRepository;

    public QuizAssignmentService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Transactional
    public void assignQuizzesToAreas() {
        // 모든 퀴즈의 사용 상태 초기화
        quizRepository.resetAllisUsed();

        // 모든 퀴즈의 ID 가져오기
        List<Long> quizIds = quizRepository.findAllQuizIds();
        // 퀴즈 ID 섞기
        Collections.shuffle(quizIds);

//        List<Area> areas = areaRepository.findAll();
        int[] areaCnt = new int[10];
        Long areaIdx = 1L;
        for (int i = 1; i <= 30; i++) {
            // 퀴즈에 해당 areaPK 세팅
            quizRepository.setAreaID(quizIds.get(i), areaIdx);
            if(i % 3 == 0){
                areaIdx++;
            }
        }

    }

}
