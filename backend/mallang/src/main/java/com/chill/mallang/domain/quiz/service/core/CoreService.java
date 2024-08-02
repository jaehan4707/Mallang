package com.chill.mallang.domain.quiz.service.core;

import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.faction.repository.FactionRepository;
import com.chill.mallang.domain.quiz.model.TotalScore;
import com.chill.mallang.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CoreService {

    private final Logger logger = LoggerFactory.getLogger(CoreService.class);

    @PersistenceContext
    private EntityManager entityManager;

    private final UserRepository userRepository;
    private final AreaRepository areaRepository;
    private final FactionRepository factionRepository;

    public CoreService(UserRepository _userRepository, AreaRepository _areaRepository, FactionRepository _factionRepository) {
        this.userRepository = _userRepository;
        this.areaRepository = _areaRepository;
        this.factionRepository = _factionRepository;
    }


    public void storeTotalScore(Long userID, Long areaID, float sum, Long factionID){
        logger.info(String.valueOf(sum));
        TotalScore totalScore = TotalScore.builder()
                .user(userRepository.getById(userID))
                .area(areaRepository.getById(areaID))
                .faction(factionRepository.getById(factionID))
                .totalScore(sum)
                .build();

        entityManager.persist(totalScore);
    }
}
