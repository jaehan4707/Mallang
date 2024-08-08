package com.chill.mallang.domain.quiz.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizResponse {
    private Long id;

    private String question;
    private String answer;
    private int difficulty; // 상 중 하
}
