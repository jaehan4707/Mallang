package com.chill.mallang.domain.study.dto.core;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WordMeanDTO {
    private Long id;
    private String mean;
    private String type;
    private String level;
    private WordDTO word;
}
