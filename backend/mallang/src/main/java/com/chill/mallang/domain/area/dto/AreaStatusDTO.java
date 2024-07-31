package com.chill.mallang.domain.area.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AreaStatusDTO {
    private Long teamId;
    private String teamName;
    private int area;
}
