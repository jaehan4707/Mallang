package com.chill.mallang.domain.study.dto.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WordMeanDTO {
    private Long id;
    private String mean;
    private String type;
    private String level;
    private WordDTO word;
}
