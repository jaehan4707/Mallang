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
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quiz")
    private Quiz quiz;

    private String answer;
    private float score;
    private int answerTime;
    private LocalDateTime created_at;
    private int check_fin;

    @PrePersist
    protected void onCreate() {
        this.check_fin = 0;
    }
}
