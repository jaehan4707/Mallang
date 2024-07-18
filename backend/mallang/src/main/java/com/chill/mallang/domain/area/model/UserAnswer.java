package com.chill.mallang.domain.area.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class UserAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    private String answer;

    private Float score;

    private Timestamp created_at;
}
