package com.chill.mallang.domain.user.dto;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
//회원가입
public class JoinRequestDTO {
    @NotNull
    private String token;
    @NotNull
    private String email;
    @NotNull
    private String nickname;

//    private Long faction_id;
    @NotNull
    private String picture;

    private Integer try_count;

    public JoinRequestDTO(){
        this.try_count = 3;
    }
}
