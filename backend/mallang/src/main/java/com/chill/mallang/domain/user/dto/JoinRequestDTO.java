package com.chill.mallang.domain.user.dto;

import com.chill.mallang.domain.faction.model.FactionType;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
//회원가입
public class JoinRequestDTO {

    @NotNull
    private String email;
    @NotNull
    private String nickname;

    @NotNull
    private String picture;

    @NotNull
    private FactionType faction;
}
