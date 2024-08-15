package com.chill.mallang.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TopUserDTO {

    private Long userId;
    private String userName;
// 티어 적용 전
//    private Integer userTier;
}
