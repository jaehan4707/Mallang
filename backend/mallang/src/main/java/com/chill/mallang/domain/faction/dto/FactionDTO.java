package com.chill.mallang.domain.faction.dto;

import com.chill.mallang.domain.user.dto.TopUserDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FactionDTO {
    private Long teamId;
    private Float teamPoint;
    private TopUserDTO topUser;
}
