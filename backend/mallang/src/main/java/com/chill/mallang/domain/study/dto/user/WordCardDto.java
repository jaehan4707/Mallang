package com.chill.mallang.domain.study.dto.user;

// StudiedWordService의 wordCard를 위한 dto

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class WordCardDto {
    private String word;
    private String meaning;
    private String example;
// 예문 데이터 없음
//    private String example;
}
