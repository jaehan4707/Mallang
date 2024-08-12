package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.AreaTopUserDTO;
import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.faction.dto.FactionDTO;
import com.chill.mallang.domain.faction.repository.FactionRepository;
import com.chill.mallang.domain.quiz.model.Answer;
import com.chill.mallang.domain.quiz.repository.AnswerRepository;
import com.chill.mallang.domain.quiz.repository.TotalScoreRepository;
import com.chill.mallang.domain.user.dto.TopUserDTO;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.errors.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

// 점령지 상세정보 1. 점령자 대표 유저 정보 조회
@Service
@RequiredArgsConstructor
public class AreaTopUserService {
    private static final Logger logger = LoggerFactory.getLogger(ChallengeRecordService.class);

    private final AreaRepository areaRepository;
    private final FactionRepository factionRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final TotalScoreRepository totalScoreRepository;

    public Map<String, Object> getAreaInfo(Long areaId, Long userTeamId) {
        Area area = areaRepository.findById(areaId).orElseThrow(()-> new RestApiException(AreaErrorCode.INVALID_TEAM));
            // 팀 별 점수 합산 및 최고 득점자 찾기
            FactionDTO myTeamInfo = calculateTeamInfo(areaId, userTeamId);
            FactionDTO oppoTeamInfo = calculateTeamInfo(areaId, getOppositeTeamId(userTeamId));

            AreaTopUserDTO topUserInfo = AreaTopUserDTO.builder()
                    .areaName(area.getName())
                    .myTeamInfo(myTeamInfo)
                    .oppoTeamInfo(oppoTeamInfo)
                    .build();

            return new HashMap<>() {{
                put("data", topUserInfo);
            }};
    }

    private FactionDTO calculateTeamInfo(Long areaId, Long teamId) {

        Float teamPoint = 0F;

        List<Float> pointList = totalScoreRepository.findTotalScoreByAreaIDAndFactionID(areaId,teamId);
        if (pointList.size() != 0) {
            teamPoint = pointList.getFirst();
        }

        Long topUserId = totalScoreRepository.findTopUserByAreaIdAndFactionId(areaId,teamId);

        TopUserDTO topUserDTO = null;
        if (topUserId != null) {
            User topUser = userRepository.findById(topUserId).get();
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
