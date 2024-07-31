package com.chill.mallang.domain.quiz.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseQuiz {
    private Long id;

    private String question;
    private String answer;
    private int difficulty; // 상 중 하
    private String type; // 주관식 or 객관식

}
