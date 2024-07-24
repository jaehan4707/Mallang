package com.chill.mallang.domain.area.dto;

import com.chill.mallang.domain.area.model.AreaLog;
import com.chill.mallang.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AreaLogs {
    private Long id;
    private String name;
    private double latitude;
    private double longitude;
    private User user;
    private List<AreaLog> areaLogs;
}
