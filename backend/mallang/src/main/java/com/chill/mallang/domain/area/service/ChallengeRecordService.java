package com.chill.mallang.domain.area.service;

import com.chill.mallang.domain.area.dto.APIresponse;
import com.chill.mallang.domain.area.dto.ChallengeRecordDTO;
import com.chill.mallang.domain.area.dto.TeamAreaLogDTO;
import com.chill.mallang.domain.area.dto.UserAreaLogDTO;
import com.chill.mallang.domain.area.model.AreaLog;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.domain.area.repository.AreaLogRepository;
import com.chill.mallang.domain.area.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChallengeRecordService {

    @Autowired
    private AreaLogRepository areaLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AreaRepository areaRepository;

    public APIresponse<ChallengeRecordDTO> getChallengeRecord(Long areaId, Long userId) {
        List<AreaLog> areaLogs = areaLogRepository.findByAreaId(areaId);
        User user = userRepository.findById(userId).orElse(null);

        ChallengeRecordDTO data;
        //없으면 빈배열 반환
        if (areaLogs.isEmpty() || user == null) {
            data = ChallengeRecordDTO.builder()
                    .userRecord(null)
                    .myTeamRecords(List.of())
                    .oppoTeamRecords(List.of())
                    .build();
        } else {
            //아군
            List<TeamAreaLogDTO> myTeamRecords = areaLogs.stream()
                    .filter(log -> isSameTeam(log.getUser(), user))
                    .map(this::toTeamAreaLogDTO)
                    .sorted(this::compareLogs)
                    .collect(Collectors.toList());

            //적군
            List<TeamAreaLogDTO> oppoTeamRecords = areaLogs.stream()
                    .filter(log -> !isSameTeam(log.getUser(), user))
                    .map(this::toTeamAreaLogDTO)
                    .sorted(this::compareLogs)
                    .collect(Collectors.toList());

            // 등수 설정
            setRankings(myTeamRecords);
            setRankings(oppoTeamRecords);

            // 사용자 기록
            TeamAreaLogDTO userRecord = myTeamRecords.stream()
                    .filter(record -> record.getUserId().equals(userId))
                    .findFirst()
                    .orElse(null);

            data = ChallengeRecordDTO.builder()
                    .userRecord(userRecord != null ? toUserAreaLogDTO(userRecord) : null)
                    .myTeamRecords(myTeamRecords)
                    .oppoTeamRecords(oppoTeamRecords)
                    .build();
        }

        return APIresponse.<ChallengeRecordDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Success")
                .data(data)
                .build();
    }

    //같은 팀인지 확인
    private boolean isSameTeam(Long logUserId, User user) {
        User logUser = userRepository.findById(logUserId).orElse(null);
        return logUser != null && logUser.getFaction().equals(user.getFaction());
    }

    // convertDTO - TeamAreaLog
    private TeamAreaLogDTO toTeamAreaLogDTO(AreaLog log) {
        User user = userRepository.findById(log.getUser()).orElse(null);
        return TeamAreaLogDTO.builder()
                .userPlace(null) // 등수는 나중에 설정
                .userId(log.getUser())
                .userName(user != null ? user.getNickname() : "Unknown")
                .userScore((int) log.getScore())
                .userPlayTime((int) (log.getCreated_at().getSecond() - log.getCreated_at().getMinute() * 60))
                .build();
    }

    // convertDTO - UserAreaLog
    private UserAreaLogDTO toUserAreaLogDTO(TeamAreaLogDTO teamLog) {
        return UserAreaLogDTO.builder()
                .userScore(teamLog.getUserScore())
                .userPlayTime(teamLog.getUserPlayTime())
                .userPlace(teamLog.getUserPlace())
                .build();
    }

    // 팀원 등수 설정 : 정렬한 인덱스값으로 적용(1등부터 시작)
    private void setRankings(List<TeamAreaLogDTO> teamRecords) {
        for (int i = 0; i < teamRecords.size(); i++) {
            teamRecords.get(i).setUserPlace(i + 1);
        }
    }

    // 기록 비교 (점수 -> 시간 순으로 비교)
    private int compareLogs(TeamAreaLogDTO log1, TeamAreaLogDTO log2) {
        int scoreComparison = log2.getUserScore().compareTo(log1.getUserScore());
        if (scoreComparison != 0) {
            return scoreComparison;
        }
        return log1.getUserPlayTime().compareTo(log2.getUserPlayTime());
    }
}
