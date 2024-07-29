package com.chill.mallang.domain.area.dto;

import com.chill.mallang.domain.area.model.AreaLog;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ChallengeRecordDTO {
    private UserAreaLogDTO userRecord;
    private List<TeamAreaLogDTO>myTeamRecords;
    private List<TeamAreaLogDTO>oppoTeamRecords;
}
