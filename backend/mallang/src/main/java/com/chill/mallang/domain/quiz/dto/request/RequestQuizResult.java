package com.chill.mallang.domain.quiz.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestQuizResult {
    @NotNull
    private Long areaID;
    @NotNull
    private Long userID;
    @NotNull
    private Long factionID;
    @NotNull
    private Long[] quizID;
}
