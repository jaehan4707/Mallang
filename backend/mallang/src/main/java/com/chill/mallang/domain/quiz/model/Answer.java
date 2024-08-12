package com.chill.mallang.domain.quiz.model;

import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "answer")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "userID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "quizID")
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "area")
    private Area area;

    @Size(max =500, message ="최대 500자 입력 가능")
    private String answer;
    private float score;
    private int answerTime;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    private int check_fin;

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
        this.check_fin = 0;
    }
}
