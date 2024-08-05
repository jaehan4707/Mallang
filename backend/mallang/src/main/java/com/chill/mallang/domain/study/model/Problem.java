package com.chill.mallang.domain.study.model;

import jakarta.persistence.*;
import org.w3c.dom.Text;

@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Question question;

    private String word;
    private Text mean;
}
