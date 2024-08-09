package com.chill.mallang.domain.user.dto;

import com.chill.mallang.domain.faction.model.Faction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FindByEmailDTO {
    private long id;
    private String email;
    private String nickname;
    private long faction_id;
    private Integer try_count;
    private int level;
    private float exp;
}
