package com.chill.mallang.domain.user.model;


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

    private String picture;
    private Integer try_count;
    private String role;
}
