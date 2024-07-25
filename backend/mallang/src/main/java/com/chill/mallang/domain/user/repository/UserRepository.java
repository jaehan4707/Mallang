package com.chill.mallang.domain.user.repository;

import com.chill.mallang.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface UserRepository extends JpaRepository<User, String> {
        Boolean existsByEmail(String email);
        User findByEmail(String email);
    }
