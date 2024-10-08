package com.chill.mallang.domain.study.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class OneWrongWordDto {
    private Long studyId ;
    private String quizTitle; // 문제 제목. 빈칸 채우기밖에 없음
    private String quizScript;
    private List<String> wordList;
}
