package com.chill.mallang.domain.area.model;


import com.chill.mallang.domain.user.model.User;
import lombok.Getter;
import lombok.Setter;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

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

}
