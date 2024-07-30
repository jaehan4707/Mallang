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
    private int difficulty;
    @NotNull
    private String question;
    @NotNull
    private String answer;
    @NotNull
    private String type;
    @NotNull
    private LocalDateTime created_at = LocalDateTime.now();

    private LocalDateTime updated_at;
    private boolean isUsed;

    @PrePersist
    protected void onCreate(){
        this.isUsed = false;
    }

    @PreUpdate
    protected void onUpdate(){
        this.updated_at = LocalDateTime.now();
    }

}
