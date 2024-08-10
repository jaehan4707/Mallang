package com.chill.mallang.domain.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class StudyGameResponseDTO {
    private long studyId;
    private String quizTitle;
    private String quizScript;
    private List<String> wordList;
}
