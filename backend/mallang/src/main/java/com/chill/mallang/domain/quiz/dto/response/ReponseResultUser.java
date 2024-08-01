package com.chill.mallang.domain.quiz.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReponseResultUser {
    private int rank;
    private double[] score = new double[3];
    private double totalScore = 0;
}
