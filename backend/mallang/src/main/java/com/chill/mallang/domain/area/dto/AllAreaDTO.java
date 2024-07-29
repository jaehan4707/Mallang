package com.chill.mallang.domain.area.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AllAreaDTO {
    private Long areaId;
    private String areaName;
    private double latitude;
    private double longitude;
}
