package com.chill.mallang.domain.study.model;

import jakarta.persistence.*;

@Entity
public class StudyGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private WordMean wordmean;

    private String question;
}
