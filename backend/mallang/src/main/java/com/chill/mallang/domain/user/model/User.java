package com.chill.mallang.domain.user.model;


import com.chill.mallang.domain.faction.model.Faction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String email;
    private String nickname;

//    @OneToOne
//    @JoinColumn(name = "faction")
//    private Faction faction;
    private String picture;
    private Integer try_count;
    private String role;
}
