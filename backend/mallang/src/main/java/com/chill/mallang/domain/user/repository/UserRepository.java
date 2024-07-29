package com.chill.mallang.domain.user.repository;

import com.chill.mallang.domain.user.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByEmail(String email);
        //USER id 탐색
        @Override
        Optional<User>findById(Long id);
        boolean existsByNickname(String nickname);
        boolean existsByEmail(String email);
}
