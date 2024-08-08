package com.chill.mallang.domain.study.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WrongWordDto {
    private Long StudyId;
    private String word;
}
