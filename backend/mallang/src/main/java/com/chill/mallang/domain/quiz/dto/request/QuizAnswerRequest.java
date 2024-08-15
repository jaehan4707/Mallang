package com.chill.mallang.domain.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Schema(description = "퀴즈 정답 제출 DTO")
@Getter
public class QuizAnswerRequest {
    @NotNull
    private Long quizId;
    @NotNull
    private Long userId;
    @NotNull
    private Long areaId;
    @NotNull
    private String userAnswer;
    @NotNull
    private int answerTime;

    private final LocalDateTime created_at = now();
}
