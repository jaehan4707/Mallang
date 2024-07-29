package com.chill.mallang.domain.user.service.impl;

import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.domain.user.service.UserService;
import com.chill.mallang.errors.errorcode.CustomErrorCode;
import com.chill.mallang.errors.exception.RestApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @ExceptionHandler
    public boolean existsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RestApiException(CustomErrorCode.EMAIL_IS_EXISTS);
        }
        return false; // 중복되지 않는 경우
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Optional<User> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new RestApiException(CustomErrorCode.NICKNAME_IS_EXISTS);
        }
        return false; // 중복되지 않는 경우
    }
}
