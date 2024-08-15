package com.chill.mallang.domain.study.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question")
    @JsonBackReference
    @NotNull
    private Question question;

    @NotNull
    private String basic_type;

    @NotNull
    private String option;

    @NotNull
    private String mean;

    @NotNull
    private int idx;
}
