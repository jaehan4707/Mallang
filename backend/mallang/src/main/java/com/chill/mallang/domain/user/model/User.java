package com.chill.mallang.domain.user.model;


import com.chill.mallang.domain.faction.model.Faction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String email;
    private String nickname;

    @ManyToOne
    @JoinColumn(name = "faction")
    private Faction faction;
    private String picture;
}
