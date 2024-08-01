package com.chill.mallang.domain.area.dto;

import com.chill.mallang.domain.quiz.model.Quiz;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AllAreaDTO {
    private Long areaId;
    private String areaName;
    private double latitude;
    private double longitude;
    private Long[] quizIds;
}
