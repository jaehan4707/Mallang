package com.chill.mallang.domain.study.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class StudyGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "word")
    private WordMean wordMean;
    private String questionText;

    @OneToOne(mappedBy = "studyGame", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Question question;
}
