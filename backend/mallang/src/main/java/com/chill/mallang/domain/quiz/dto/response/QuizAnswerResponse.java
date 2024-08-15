package com.chill.mallang.domain.quiz.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuizAnswerResponse {
    private long id;
    private String question;
    private String answer;
}