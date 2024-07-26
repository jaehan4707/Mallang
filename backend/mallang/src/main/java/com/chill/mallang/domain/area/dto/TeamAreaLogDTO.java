package com.chill.mallang.domain.area.dto;

import com.chill.mallang.domain.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TeamAreaLogDTO {
    private Integer userPlace;
    private Long userId;
    private String userName;
    private Integer userScore;
    private Integer userPlayTime;
}
