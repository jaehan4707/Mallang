package com.chill.mallang.domain.quiz.model;

import com.chill.mallang.domain.area.model.Area;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "area")
    private Area area;

    @NotNull
    private String question;
    @NotNull
    private int difficulty;
    @NotNull
    private LocalDateTime created_at;

    private LocalDateTime updated_at;
    private boolean isUsed;
}
