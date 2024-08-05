package com.chill.mallang.domain.word.model;

import jakarta.persistence.*;

@Entity
public class WordMean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Word word;

    @Column(length = 500)
    private String mean;
}
