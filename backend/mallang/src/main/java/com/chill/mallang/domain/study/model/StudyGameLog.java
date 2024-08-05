package com.chill.mallang.domain.study.model;

import com.chill.mallang.domain.user.model.User;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.hibernate.type.descriptor.jdbc.TinyIntJdbcType;

public class StudyGameLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private StudyGame studyGame;

    private boolean result;
}
