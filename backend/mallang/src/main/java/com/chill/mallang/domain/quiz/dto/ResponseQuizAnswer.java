package com.chill.mallang.domain.quiz.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseQuizAnswer {
    private long id;
    private String question;
    private String answer;
}
