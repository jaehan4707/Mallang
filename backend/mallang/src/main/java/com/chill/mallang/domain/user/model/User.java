package com.chill.mallang.domain.user.model;


import com.chill.mallang.domain.faction.model.Faction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

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

}
