package com.chill.mallang.domain.study.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;
import java.util.Map;

@Getter
@Builder
public class UserStudyLogResponseDTO {
    private String quizTitle;
    private String quizScript;

    private List<Map<String, String>> wordList;

    private boolean result;

    private long systemAnswer;
}
