package com.chill.mallang.domain.study.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "studyGame")
    private StudyGame studyGame;

    @OneToMany(mappedBy = "question")
    private List<Problem> problems;

}
