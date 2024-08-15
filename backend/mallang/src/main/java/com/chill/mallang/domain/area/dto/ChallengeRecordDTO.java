package com.chill.mallang.domain.area.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ChallengeRecordDTO {
    private ResponseTotalUserInfo userRecord;
    private List<ResponseTotalTeamInfo>myTeamRecords;
    private List<ResponseTotalTeamInfo>oppoTeamRecords;
}
