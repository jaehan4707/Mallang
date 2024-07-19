package com.chill.mallang.domain.area.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AreaLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long arealog_id;

    @ManyToOne
    @JoinColumn( name = "area_id" )
    private Area area_id;

    private long user_id;
    private LocalDateTime created_at;
    @NotNull
    private float score;
}
