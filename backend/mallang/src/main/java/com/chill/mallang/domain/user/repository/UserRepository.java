package com.chill.mallang.domain.user.repository;

import com.chill.mallang.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Boolean existsByEmail(String email);
    User findByEmail(String email);

    Optional<User> findById(Long id);
}
