package com.chill.mallang.domain.study.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import org.w3c.dom.Text;

public class StudyGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private WordMean wordmean;

    private Text question;
}
