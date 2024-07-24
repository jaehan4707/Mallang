package com.chill.mallang.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//회원가입
public class JoinDTO {
    private String email;

    private String nickname;

//    private Long faction_id;

    private String picture;

    private Integer try_count;

    public JoinDTO(){
        this.try_count = 3;
    }
}
