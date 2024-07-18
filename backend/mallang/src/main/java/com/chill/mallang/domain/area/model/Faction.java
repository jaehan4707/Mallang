package com.chill.mallang.domain.area.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Faction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String name;

    @OneToMany(mappedBy = "faction", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> areaLogs;
}
