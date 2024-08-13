package com.chill.mallang.domain.area.dto;

import com.chill.mallang.domain.faction.dto.FactionDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AreaTopUserDTO {
    private String areaName;
    private double latitude;
    private double longitude;
    private FactionDTO myTeamInfo;
    private FactionDTO oppoTeamInfo;
}
