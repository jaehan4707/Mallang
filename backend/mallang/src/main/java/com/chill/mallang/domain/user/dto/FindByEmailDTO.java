package com.chill.mallang.domain.user.dto;

import com.chill.mallang.domain.faction.model.FactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindByEmailDTO {
    private String nickname;
    private String faction;
    private Integer try_count;

    public FindByEmailDTO(String nickname, String faction, Integer tryCount) {
        this.nickname = nickname;
        this.faction = faction;
        this.try_count = tryCount;
    }
}
