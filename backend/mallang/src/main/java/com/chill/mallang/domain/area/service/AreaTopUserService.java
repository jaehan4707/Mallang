package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.APIresponse;
import com.chill.mallang.domain.area.dto.AreaTopUserDTO;
import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.faction.dto.FactionDTO;
import com.chill.mallang.domain.faction.repository.FactionRepository;
import com.chill.mallang.domain.user.dto.TopUserDTO;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.errors.exception.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

// 점령지 상세정보 1. 점령자 대표 유저 정보 조회
@Service
public class AreaTopUserService {
    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private FactionRepository factionRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getAreaInfo(Long areaId, Long userTeamId) {
        Optional<Area> area = areaRepository.findById(areaId);


        if (area.isPresent() && userTeamId != null) {

            List<User> users = userRepository.findAll();

            // 팀 별 점수 합산 및 최고 득점자 찾기
            FactionDTO myTeamInfo = calculateTeamInfo(users, userTeamId);
            FactionDTO oppoTeamInfo = calculateTeamInfo(users, getOppositeTeamId(userTeamId));

            AreaTopUserDTO topUserInfo = AreaTopUserDTO.builder()
                    .areaName(area.get().getName())
                    .myTeamInfo(myTeamInfo)
                    .oppoTeamInfo(oppoTeamInfo)
                    .build();

            return new HashMap<>(){{
                put("data",topUserInfo);
            }};
        } else {
            throw new RestApiException(AreaErrorCode.INVALID_PARAMETER);
        }
    }

    private FactionDTO calculateTeamInfo(List<User> users, Long teamId) {
        List<User> teamMembers = users.stream()
                .filter(user -> (user.getFaction() != null) && Objects.equals(user.getFaction().getId(), teamId))
                .toList();

        int teamPoint = teamMembers.stream()
                .mapToInt(user -> Optional.ofNullable(user.getTry_count()).orElse(0))
                .sum();

        User topUser = teamMembers.stream()
                .max(Comparator.comparingInt(user -> Optional.ofNullable(user.getTry_count()).orElse(0)))
                .orElse(null);

        TopUserDTO topUserDTO = topUser == null ? null : TopUserDTO.builder()
                .userId(topUser.getId())
                .userName(topUser.getNickname())
                .build();

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
