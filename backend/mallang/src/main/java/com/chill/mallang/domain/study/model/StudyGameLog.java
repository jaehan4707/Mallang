package com.chill.mallang.domain.study.model;

import com.chill.mallang.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyGameLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    private StudyGame studyGame;

    @ManyToOne
    private WordMean wordMean;

    @NotNull
    private LocalDateTime created_at;

    @NotNull
    private boolean result;
}
