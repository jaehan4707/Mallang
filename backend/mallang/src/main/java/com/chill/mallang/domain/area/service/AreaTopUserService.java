package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.AreaTopUserDTO;
import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.area.repository.AreaRepository;
import com.chill.mallang.domain.faction.dto.FactionDTO;
import com.chill.mallang.domain.faction.repository.FactionRepository;
import com.chill.mallang.domain.user.dto.TopUserDTO;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional; //두 객체를 비교할 때 null 체크를 자동으로 처리

@Service
public class AreaTopUserService {
    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private FactionRepository factionRepository;

    @Autowired
    private UserRepository userRepository;

    public AreaTopUserDTO getAreaInfo(Long areaId, Long userTeamId) {
        Area area = areaRepository.findById(areaId).orElseThrow(() -> new IllegalArgumentException("Invalid area ID"));
        List<User> users = userRepository.findAll();

        // 팀 별 점수 합산 및 최고 득점자 찾기
        FactionDTO myTeamInfo = calculateTeamInfo(users, userTeamId);
        FactionDTO oppoTeamInfo = calculateTeamInfo(users, getOppositeTeamId(userTeamId));

        return AreaTopUserDTO.builder()
                .areaName(area.getName())
                .myTeamInfo(myTeamInfo)
                .oppoTeamInfo(oppoTeamInfo)
                .build();
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













//    @Autowired
//    private FactionRepository factionRepository;
//
//    @Autowired
//    private AreaRepository areaRepository;
//
//    public AreaTopUserDTO getTopUSerInfo(Long areaId, Long factionId) {
//        //현재 점령지
//        Area area = areaRepository.findByAreaId(areaId);
//
//        // 유저 소속 팀
//        Faction myTeamInfo = factionRepository.findByFactionId(factionId);
//        FactionDTO myTeamInfoDto = convertToDto(myTeamInfo);
//
//        // 상대 팀 -> 유저 소속 팀 기준으로 찾기
//        Long oppoTeamId = (long) (factionId == 1?2:1);
//        Faction oppoTeamInfo = factionRepository.findByFactionId(oppoTeamId);
//        FactionDTO oppoTeamInfoDto = convertToDto(oppoTeamInfo);
//
//        //dto
//        AreaTopUserDTO areaTopUserDTO = AreaTopUserDTO.builder()
//                .areaName(area.getName())
//                .myTeamInfo(myTeamInfoDto)
//                .oppoTeamInfo(oppoTeamInfoDto)
//                .build();
//
//        return areaTopUserDTO;
//    }
//
//    // dto 변환 메서드
//    private FactionDTO convertToDto(Faction faction){
//        User topUser = faction.getTopUser();
//        TopUserDTO topUserDto = topUser != null ? new TopUserDTO.Builder()
//                .userId(topUser.getUserId())
//                .userName(topUser.getUserName())
//                .build() : null;
//
//        return new FactionDTO.Builder()
//                .teamId(faction.getTeamId())
//                .teamPoint(faction.getTotalPoints())
//                .topUser(topUserDto)
//                .build();
//
//    }

