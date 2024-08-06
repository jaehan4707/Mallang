package com.chill.mallang.domain.study.dto;

import com.chill.mallang.domain.study.dto.core.WordMeanDTO;
import com.chill.mallang.domain.study.model.StudyGame;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserStudyLogRequestDTO {

    private long userId;

    @ManyToOne
    private StudyGame studyGame;

    private WordMeanDTO wordMeanDTO;

    private String word;
    private String mean;

    private boolean result;
}
