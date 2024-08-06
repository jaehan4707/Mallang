package com.chill.mallang.domain.study.dto;

import com.chill.mallang.domain.study.dto.core.WordMeanDTO;
import com.chill.mallang.domain.study.model.StudyGame;
import jakarta.persistence.ManyToOne;

public class UserStudyLogResponseDTO {
    private long userId;

    @ManyToOne
    private StudyGame studyGame;

    private WordMeanDTO wordMeanDTO;

    private boolean result;
}
