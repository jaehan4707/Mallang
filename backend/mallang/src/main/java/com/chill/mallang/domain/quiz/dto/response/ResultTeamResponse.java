package com.chill.mallang.domain.quiz.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResultTeamResponse {
    private int[] teamRank = new int[3];
    private double teamScore = 0;
    private double oppoScore = 0;
}
