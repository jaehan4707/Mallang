package com.chill.mallang.domain.quiz.dto.request;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

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
    // 최종제출 여부 0 or 1 -> 최종 적으로 True;
    // 1 인 경우만 결과 도축
    // quizID userID -> Answer 제일 최신 순서로 False
}
