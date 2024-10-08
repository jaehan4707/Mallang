package com.chill.mallang.domain.user.service.impl;

import com.chill.mallang.domain.user.dto.FindByEmailDTO;
import com.chill.mallang.domain.user.errors.CustomUserErrorCode;
import com.chill.mallang.domain.user.jwt.JWTUtil;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.domain.user.service.UserService;
import com.chill.mallang.errors.exception.RestApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    @ExceptionHandler
    public boolean existsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new RestApiException(CustomUserErrorCode.EMAIL_IS_EXISTS);
        }
        return false; // 중복되지 않는 경우
    }

    @Override
    public boolean existsByNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new RestApiException(CustomUserErrorCode.NICKNAME_IS_EXISTS);
        }
        return false; // 중복되지 않는 경우
    }

    @Override
    public Optional<FindByEmailDTO> findByEmail(String email) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.orElseThrow(() -> new RestApiException(CustomUserErrorCode.EMAIL_IS_EXISTS));
        logger.info("findByEmail return " + user.toString());

        FindByEmailDTO findByEmailDTO = FindByEmailDTO.builder()
                .email(user.getEmail())
                .id(user.getId())
                .faction_id(user.getFaction().getId())
                .nickname(user.getNickname())
                .try_count(user.getTry_count())
                .level(user.getLevel())
                .exp(user.getExp())
                .build();

        return Optional.of(findByEmailDTO);
    }
    @Override
    public Map<String, Object> findByEmailFromToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RestApiException(CustomUserErrorCode.AUTHENTICATED_FAILED);
        }

        String email = jwtUtil.extractEmail(token.substring(7));
        Optional<FindByEmailDTO> userDTO = findByEmail(email);
        logger.info("userDTO: " + userDTO);

        Map<String, Object> response = new HashMap<>();

        if (userDTO.isPresent()) {
            response.put("data", userDTO.get());
        } else {
            throw new RestApiException(CustomUserErrorCode.JOIN_IS_FAILED);
        }

        return response;
    }

    public void addExp(Long userID, float exp) {
        float nowExp = userRepository.findExpById(userID);
        User user = userRepository.getUserById(userID);
        float nextExp = user.getExp() + exp;

        if(nextExp >= user.getLevel() * 200) {
            nextExp -= user.getLevel() * 200;
            userRepository.updateUserLevel(user.getId(), user.getLevel()+1);
        }
        userRepository.updateUserExp(user.getId(), nextExp);
    }
}

