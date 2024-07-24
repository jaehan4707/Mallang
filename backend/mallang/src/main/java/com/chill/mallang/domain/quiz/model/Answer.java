package com.chill.mallang.domain.quiz.model;

import com.chill.mallang.domain.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "quiz")
    private Quiz quiz;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    private String answer;
    private float score;
    private LocalDateTime created_at;
}
