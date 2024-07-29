package com.chill.mallang.domain.area.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private long id;

    @ManyToOne
    @JoinColumn( name = "area" )
    @JsonBackReference
    private Area area;

    private long user;
    private LocalDateTime created_at;
    private int playtime;
    @NotNull
    private float score;
}
