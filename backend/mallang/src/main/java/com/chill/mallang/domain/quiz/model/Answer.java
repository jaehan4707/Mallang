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
    private long answer_id;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz_id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    private String answer;
    private float score;
    private LocalDateTime created_at;
}
