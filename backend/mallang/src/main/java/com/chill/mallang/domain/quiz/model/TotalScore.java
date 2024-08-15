package com.chill.mallang.domain.quiz.model;

import com.chill.mallang.domain.area.model.Area;
import com.chill.mallang.domain.faction.model.Faction;
import com.chill.mallang.domain.user.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "area")
    private Area area;
    @ManyToOne
    @JoinColumn(name = "faction")
    private Faction faction;

    @NotNull
    private float totalScore;
    @NotNull
    private LocalDateTime created_at;

    @PrePersist
    protected void onCreate() {
        this.created_at = LocalDateTime.now();
    }
}
