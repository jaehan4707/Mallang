package com.chill.mallang.domain.area.dto;

import com.chill.mallang.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AreaDTO {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    private User user;
}
