package com.chill.mallang.domain.user.repository;

import com.chill.mallang.domain.user.model.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByEmail(String email);
        //USER id 탐색
        @Override
        Optional<User>findById(Long id);
        boolean existsByNickname(String nickname);
        boolean existsByEmail(String email);

        @Query("SELECT u.exp FROM User u WHERE u.id = :userID")
        int findExpById(Long userID);

        User getUserById(Long id);

        @Modifying
        @Transactional
        @Query("UPDATE User u SET u.exp = :exp WHERE u.id = :userID")
        void updateUserExp(Long userID, float exp);

        @Modifying
        @Transactional
        @Query("UPDATE User u SET u.level = :level WHERE u.id = :userID")
        void updateUserLevel(Long userID, Integer level);

        @Modifying
        @Query("UPDATE User  u SET u.try_count = u.try_count -1 WHERE u.id = :userID ")
        void minusTryCount(@Param("userID") long userID);
}
