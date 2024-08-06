package com.chill.mallang.domain.study.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class StudyGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private WordMean wordMean;
    private String questionText;

    @OneToOne(mappedBy = "studyGame", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Question question;
}
