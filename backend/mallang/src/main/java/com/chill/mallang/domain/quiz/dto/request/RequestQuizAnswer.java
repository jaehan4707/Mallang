package com.chill.mallang.domain.quiz.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Data
public class RequestQuizAnswer {
    @NotNull
    private Long quizId;
    @NotNull
    private Long userId;
    @NotNull
    private String userAnswer;

    private int answerTime;
    private LocalDateTime created_at = now();

}
