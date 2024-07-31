package com.chill.mallang.domain.quiz.model;

import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "area")
    private Area area;

    @Size(max =500, message ="최대 500자 입력 가능")
    private String answer;
    private double score;
    private int answerTime;
    private LocalDateTime created_at;

    private int check_fin;

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
        this.check_fin = 0;
    }

    @Builder
    public Answer(User user, Quiz quiz, String answer, double score, int answerTime, int check_fin) {
        this.user = user;
        this.quiz = quiz;
        this.answer = answer;
        this.score = score;
        this.answerTime = answerTime;
        this.check_fin = check_fin;
    }
}
