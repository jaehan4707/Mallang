package com.chill.mallang.domain.user.service.impl;

import com.chill.mallang.domain.user.dto.FindByEmailDTO;
import com.chill.mallang.domain.user.jwt.JWTUtil;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.domain.user.service.UserService;
import com.chill.mallang.errors.errorcode.CustomErrorCode;
import com.chill.mallang.errors.exception.RestApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            throw new RestApiException(CustomErrorCode.EMAIL_IS_EXISTS);
        }
        return false; // 중복되지 않는 경우
    }

    @Override
    public boolean existsByNickname(String nickname) {
        if (userRepository.existsByNickname(nickname)) {
            throw new RestApiException(CustomErrorCode.NICKNAME_IS_EXISTS);
        }
        return false; // 중복되지 않는 경우
    }

    @Override
    public Optional<FindByEmailDTO> findByEmail(String email) {
        logger.info("findByEmail" + userRepository.findByEmail(email).toString());
        return userRepository.findByEmail(email).map(user -> new FindByEmailDTO(
                user.getNickname(),
                user.getFaction().getName().name(), // FactionType을 문자열로 변환
                user.getTry_count()
        ));
    }

    @Override
    public Map<String, Object> findByEmailFromToken(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RestApiException(CustomErrorCode.INVALID_PARAMETER);
        }
        String email = jwtUtil.extractEmail(token.substring(7));
        Optional<FindByEmailDTO> userDTO = findByEmail(email);
        logger.info("userDTO: " + userDTO);
        Map<String, Object> response = new HashMap<>();
        if (userDTO.isPresent()) {
            response.put("status", 200);
            response.put("success", "회원조회에 성공했습니다.");
            response.put("data", userDTO.get());
        } else {
            throw new RestApiException(CustomErrorCode.RESOURCE_NOT_FOUND);
        }
        return response;
    }
}

