//package com.chill.mallang.domain.area.service;
//
//import com.chill.mallang.domain.area.dto.ChallengeRecordDTO;
//import com.chill.mallang.domain.area.dto.TeamAreaLogDTO;
//import com.chill.mallang.domain.area.dto.UserAreaLogDTO;
//
//import com.chill.mallang.domain.area.model.Area;
//import com.chill.mallang.domain.area.model.AreaLog;
//import com.chill.mallang.domain.area.repository.AreaRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class ChallengeRecordService {
//
//    @Autowired
//    private AreaRepository areaRepository;
//
//    public ChallengeRecordDTO getChallengeRecord(Long areaId, Long userId) {
//        // Area관련 데이터 조회
//        Area area = areaRepository.findById(areaId).orElse(null);
//
//        if (area == null) {
//            return ChallengeRecordDTO.builder()
//                    .userRecord(null)
//                    .myTeamRecords(List.of())
//                    .oppoTeamRecords(List.of())
//                    .build();
//        }
//
//        // AreaLogs에서 사용자 기록 + 팀 기록 가져오기
//        List<AreaLog> areaLogs = area.getAreaLogs();
//
//        List<TeamAreaLogDTO> myTeamRecords = areaLogs.stream()
//                .filter(log -> log.getUser().getFaction().getName().equals("myTeam"))
//                .map(log -> TeamAreaLogDTO.builder()
//                        .userId(log.getUser().getId())
//                        .userName(log.getUser().getNickname())
//                        .userScore((int) log.getScore())
//                        .userPlayTime(log.getPlayTime())
//                        .build())
//                .sorted(Comparator.comparingInt(TeamAreaLogDTO::getUserScore))
//                .collect(Collectors.toList());
//
//        List<TeamAreaLogDTO> oppoTeamRecords = areaLogs.stream()
//                .filter(log -> log.getUser().getFaction().getName().equals("oppoTeam"))
//                .map(log -> TeamAreaLogDTO.builder()
//                        .userId(log.getUser().getId())
//                        .userName(log.getUser().getNickname())
//                        .userScore((int) log.getScore())
//                        .userPlayTime(log.getPlayTime())
//                        .build())
//                .sorted(Comparator.comparingInt(TeamAreaLogDTO::getUserScore))
//                .collect(Collectors.toList());
//
//        // 등수 할당 로직
//        assignRanks(myTeamRecords);
//        assignRanks(oppoTeamRecords);
//
//        // 사용자 기록 찾기
//        UserAreaLogDTO userRecord = areaLogs.stream()
//                .filter(log -> log.getUser().getId().equals(userId))
//                .map(log -> UserAreaLogDTO.builder()
//                        .userScore((int) log.getScore())
//                        .userPlayTime(log.getPlayTime())
//                        .userPlace(getUserRank(log.getUser().getId(), myTeamRecords))
//                        .build())
//                .findFirst()
//                .orElse(null);
//
//        return ChallengeRecordDTO.builder()
//                .userRecord(userRecord)
//                .myTeamRecords(myTeamRecords)
//                .oppoTeamRecords(oppoTeamRecords)
//                .build();
//    }
//
//    //등수 찾기
//    private void assignRanks(List<TeamAreaLogDTO> teamRecords) {
//        for (int i = 0; i < teamRecords.size(); i++) {
//            teamRecords.get(i).setUserPlace(i + 1);
//        }
//    }
//
//    private int getUserRank(Long userId, List<TeamAreaLogDTO> teamRecords) {
//        for (TeamAreaLogDTO record : teamRecords) {
//            if (record.getUserId().equals(userId)) {
//                return record.getUserPlace();
//            }
//        }
//        return -1; // 사용자 기록이 없는 경우
//    }
//}
