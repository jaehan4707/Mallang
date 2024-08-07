package com.chill.mallang.domain.quiz.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamRankResponse {
    private String nickName;
    private Float totalScore;
}
