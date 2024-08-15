package com.chill.mallang.domain.area.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyTopUserResponse {
    private String nickname;
    private float score;
}
