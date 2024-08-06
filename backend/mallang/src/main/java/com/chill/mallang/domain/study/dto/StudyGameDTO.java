package com.chill.mallang.domain.study.dto;

import com.chill.mallang.domain.study.model.WordMean;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class StudyGameDTO {
    private long studyId;
    private String quizScript;
    private List<Map<String, String>> wordList;
}
