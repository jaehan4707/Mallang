package com.chill.mallang.domain.study.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class UserStudyLogResponseDTO {

    private String quizScript;

    private List<Map<String, String>> wordList;

    private boolean result;

    private long systemAnswer;
}
