package com.chill.mallang.domain.faction.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Faction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long faction_id;

    @NotNull
    private String name;
}
