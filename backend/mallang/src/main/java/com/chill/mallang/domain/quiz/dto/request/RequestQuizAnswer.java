package com.chill.mallang.domain.quiz.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Data
public class RequestQuizAnswer {
    // 점령자 아이디
    private Long quizId;
    private Long userId;

    private String userAnswer;
    private int answerTime;
    private LocalDateTime created_at = now();

}
