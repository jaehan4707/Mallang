package com.chill.mallang.domain.area.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseTotalTeamInfo {
    @NotNull
    private Integer userPlace;
    @NotNull
    private Long userId;
    @NotNull
    private String userName;
    @NotNull
    private Integer userScore;
    @NotNull
    private Integer userPlayTime;
}
