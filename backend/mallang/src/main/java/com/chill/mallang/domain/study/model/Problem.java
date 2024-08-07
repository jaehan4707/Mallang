package com.chill.mallang.domain.study.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question")
    @JsonBackReference
    private Question question;

    private String basic_type;
    private String obtion;
    private String mean;
    private int idx;
}
