package com.chill.mallang.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinResponseDTO {
    private String email;
    private String nickname;
    private String role;
    private String picture;

    private Integer try_count;

    public JoinResponseDTO() {
        this.try_count = 3;
    }

    // Getters and Setters
}