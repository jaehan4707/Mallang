package com.chill.mallang.domain.quiz.service.core;

import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.quiz.model.TotalScore;
import com.chill.mallang.domain.quiz.service.QuizService;
import com.chill.mallang.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CoreService {

    private final Logger logger = LoggerFactory.getLogger(CoreService.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AreaRepository areaRepository;


    public void storeTotalScore(Long userID, Long areaID, float sum){
        logger.info(String.valueOf(sum));
        TotalScore totalScore = TotalScore.builder()
                .user(userRepository.getById(userID))
                .area(areaRepository.getById(areaID))
                .totalScore(sum)
                .build();

        entityManager.persist(totalScore);
    }
}
