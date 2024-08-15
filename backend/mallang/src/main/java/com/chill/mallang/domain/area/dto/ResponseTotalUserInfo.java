package com.chill.mallang.domain.area.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResponseTotalUserInfo {
    private float userScore;
    private Integer userPlayTime;
    private Integer userPlace;
}
