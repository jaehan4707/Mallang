package com.chill.mallang.domain.study.model;

import com.chill.mallang.domain.user.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Getter
@Setter
public class StudyGameLog {
    // 풀었던 문제 또 나올 수도 있도록?
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private StudyGame studyGame;

    @OneToOne
    private WordMean wordMean;

    private boolean result;
}
