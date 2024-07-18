package com.chill.mallang.domain.area.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class AreaLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private Long user_id;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    private Timestamp created_at;

    private Float score;
}
