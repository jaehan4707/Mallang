package com.chill.mallang.domain.quiz.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Schema(description = "전체 결과 요청 DTO")
@Getter
public class QuizResultRequest {
    @NotNull
    private Long areaID;
    @NotNull
    private Long userID;
    @NotNull
    private Long factionID;
    @NotNull
    private Long[] quizID;
}
