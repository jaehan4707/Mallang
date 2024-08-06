package com.chill.mallang.domain.user.dto;

import com.chill.mallang.domain.faction.model.Faction;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FindByEmailDTO {
    private long id;
    private String email;
    private String nickname;
    private long faction_id;
    private Integer try_count;

    public FindByEmailDTO(long id, String email, String nickname, long faction_id, Integer tryCount) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.faction_id = faction_id;
        this.try_count = tryCount;
    }
}
