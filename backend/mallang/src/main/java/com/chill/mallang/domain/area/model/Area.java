package com.chill.mallang.domain.area.model;


import com.chill.mallang.domain.quiz.model.Quiz;
import com.chill.mallang.domain.user.model.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Getter
@Setter
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @OneToMany(mappedBy="area", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<AreaLog> areaLogs;

    // 문제 pk 3개
    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz> quizzes;
}
