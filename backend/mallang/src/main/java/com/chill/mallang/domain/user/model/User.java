package com.chill.mallang.domain.user.model;


import com.chill.mallang.domain.faction.model.Faction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String email;
    @NotNull
    private String nickname;
    @NotNull
    private String picture;

    @ManyToOne
    @JoinColumn(name ="faction", nullable = false)
    private Faction faction;

    private Integer try_count;
    private String role;

    private int level;
    private int exp;

    @PrePersist
    protected void onCreate() {
        if(this.level == 0) level = 1;
    }
}
