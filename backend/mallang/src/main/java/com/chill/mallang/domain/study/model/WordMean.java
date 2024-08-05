package com.chill.mallang.domain.study.model;

import jakarta.persistence.*;
import org.w3c.dom.Text;

@Entity
public class WordMean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Word word;

    private Text mean;

    private String type;
    private String level;
}
