package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.AreaTopUserDTO;
import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.faction.dto.FactionDTO;
import com.chill.mallang.domain.faction.repository.FactionRepository;
import com.chill.mallang.domain.quiz.model.Answer;
import com.chill.mallang.domain.quiz.repository.AnswerRepository;
import com.chill.mallang.domain.user.dto.TopUserDTO;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.errors.exception.RestApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

// 점령지 상세정보 1. 점령자 대표 유저 정보 조회
@Service
public class AreaTopUserService {
    private static final Logger logger = LoggerFactory.getLogger(ChallengeRecordService.class);

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private FactionRepository factionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AnswerRepository answerRepository;

    public Map<String, Object> getAreaInfo(Long areaId, Long userTeamId) {
        Optional<Area> area = areaRepository.findById(areaId);

        // team은 1 혹은 2
        if (area.isPresent() && userTeamId != null) {
            if (userTeamId != 1 && userTeamId != 2) {
                throw new RestApiException(AreaErrorCode.INVALID_TEAM);
            }

            List<Answer> answers = answerRepository.findByAreaId(areaId);
            logger.info(LocalDate.now().toString());
            logger.info("answers List is : " + answers.toString());

            // 팀 별 점수 합산 및 최고 득점자 찾기
            FactionDTO myTeamInfo = calculateTeamInfo(answers, userTeamId);
            FactionDTO oppoTeamInfo = calculateTeamInfo(answers, getOppositeTeamId(userTeamId));

            AreaTopUserDTO topUserInfo = AreaTopUserDTO.builder()
                    .areaName(area.get().getName())
                    .myTeamInfo(myTeamInfo)
                    .oppoTeamInfo(oppoTeamInfo)
                    .build();

            return new HashMap<>() {{
                put("data", topUserInfo);
            }};
        } else {
            throw new RestApiException(AreaErrorCode.INVALID_PARAMETER);
        }
    }

    private FactionDTO calculateTeamInfo(List<Answer> answers, Long teamId) {
        int teamPoint = 0;
        User topUser = null;
        int maxScore = 0;
        for (Answer answer : answers) {
            if (answer.getUser().getFaction() != null && answer.getCheck_fin() == 1) {
                logger.info("start calculate TopUser and team point!");
                if (answer.getUser().getFaction().getId() == teamId){
                    logger.info("start calculate team point");
                    double score = answer.getScore();
                    int tryCount = answer.getAnswerTime();
                    teamPoint += (int) score;
                    if (score > maxScore) {
                        maxScore = (int)score;
                        topUser = answer.getUser();
                        logger.info("topUser is changed");
                    }
                }
            }
        }



        TopUserDTO topUserDTO = null;
        if (topUser != null) {
            topUserDTO = TopUserDTO.builder()
                    .userId(topUser.getId())
                    .userName(topUser.getNickname())
                    .build();
        }

        return FactionDTO.builder()
                .teamId(teamId)
                .teamPoint(teamPoint)
                .topUser(topUserDTO)
                .build();
    }

    private Long getOppositeTeamId(Long userTeamId) {
        return userTeamId == 1 ? 2L : 1L;
    }
}
