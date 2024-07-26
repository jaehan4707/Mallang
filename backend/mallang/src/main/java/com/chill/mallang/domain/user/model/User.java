package com.chill.mallang.domain.user.model;


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
    private Integer try_count;
    private String role;
}
