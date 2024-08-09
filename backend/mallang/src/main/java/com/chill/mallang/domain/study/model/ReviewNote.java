package com.chill.mallang.domain.study.model;

import com.chill.mallang.domain.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Getter
@Entity
public class ReviewNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private User user;

    @OneToOne
    private StudyGameLog studyGameLog;

    @OneToOne
    private Word word;

    private LocalDateTime created_at;
}
