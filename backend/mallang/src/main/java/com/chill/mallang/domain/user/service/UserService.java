package com.chill.mallang.domain.user.service;

import com.chill.mallang.domain.user.model.User;
import java.util.Optional;

public interface UserService {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    boolean existsById(Long id);
}
