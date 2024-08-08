package com.chill.mallang.domain.study.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class StudyGameDTO {
    private long studyId;
    private String quizTitle;
    private String quizScript;
    private List<String> wordList;
}
