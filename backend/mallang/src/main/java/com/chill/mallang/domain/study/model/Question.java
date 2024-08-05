package com.chill.mallang.domain.study.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private StudyGame studyGame;

    @OneToMany(mappedBy = "question")
    private List<Problem> problems;

}
