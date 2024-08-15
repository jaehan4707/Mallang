package com.chill.mallang.domain.quiz.service.core;

import com.chill.mallang.domain.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizAssignmentService {
    private final QuizRepository quizRepository;

    @Transactional
    public void assignQuizzesToAreas() {
        quizRepository.resetAllArea();

        List<Long> quizIds = quizRepository.findAllQuizIds();

        Collections.shuffle(quizIds);

        int[] areaCnt = new int[10];
        Long areaIdx = 1L;

        for (int i = 1; i <= 30; i++) {
            quizRepository.setAreaID(quizIds.get(i), areaIdx);
            if(i % 3 == 0){areaIdx++;}
        }

    }

}
