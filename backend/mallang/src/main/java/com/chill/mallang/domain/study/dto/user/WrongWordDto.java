package com.chill.mallang.domain.study.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WrongWordDto {
    private Long StudyId;
    private String word;
}
