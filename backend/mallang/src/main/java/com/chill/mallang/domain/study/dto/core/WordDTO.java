package com.chill.mallang.domain.study.dto.core;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WordDTO {
    private Long id;
    private String word;
}
