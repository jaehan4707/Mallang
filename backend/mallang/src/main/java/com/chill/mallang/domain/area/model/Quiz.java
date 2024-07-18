package com.chill.mallang.domain.area.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @Lob
    private String question;

    private Integer difficulty;

    private Timestamp created_at;

    private Timestamp updated_at;

    private Boolean is_used;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserAnswer> userAnswers;
}
