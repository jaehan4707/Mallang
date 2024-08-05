package com.chill.mallang.domain.study.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String word;

    @OneToMany(mappedBy = "word")
    private List<WordMean> wordMean;

}
