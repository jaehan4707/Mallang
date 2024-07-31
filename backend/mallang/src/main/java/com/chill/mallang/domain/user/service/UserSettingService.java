package com.chill.mallang.domain.user.service;


import com.chill.mallang.domain.user.errors.CustomUserErrorCode;
import com.chill.mallang.domain.user.jwt.JWTUtil;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.errors.errorcode.CustomErrorCode;
import com.chill.mallang.errors.exception.RestApiException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserSettingService {
    private static final Logger logger = LoggerFactory.getLogger(UserSettingService.class);
    private final JWTUtil jwtUtil;
    private final UserRepository userRepository;

    public UserSettingService(JWTUtil jwtUtil, UserRepository userRepository){
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public Map<String, Object> updateNickname(HttpServletRequest request, String nickname) {
        if (!request.getMethod().equalsIgnoreCase("PATCH")) {
            throw new RestApiException(CustomUserErrorCode.METHOD_NOT_ALLOWED);
        }
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.info(nickname);
        logger.info(Boolean.toString(userRepository.existsByNickname(nickname)));
        if(userRepository.existsByNickname(nickname)){
            throw new RestApiException(CustomUserErrorCode.NICKNAME_IS_EXISTS);
        }
        String email = jwtUtil.extractEmail(token.substring(7));
        Optional<User> optionalUser = userRepository.findByEmail(email);
        logger.info("optionalUser: " + optionalUser);
        Map<String, Object> response = new HashMap<>();
        if (optionalUser.isPresent()) {
            Map<String, String> dataMap = new HashMap<>();
            User user = optionalUser.get();
            user.setNickname(nickname);
            userRepository.save(user);
            dataMap.put("nickname", user.getNickname());
            response.put("status", 200);
            response.put("success", "닉네임 변경에 성공했습니다.");
            response.put("data", dataMap);
        } else {
            throw new RestApiException(CustomUserErrorCode.USER_NOT_FOUND);
        }
        return response;
    }
    public Map<String, Object> deleteUser(HttpServletRequest request) {
        if (!request.getMethod().equalsIgnoreCase("Delete")) {
            throw new RestApiException(CustomUserErrorCode.METHOD_NOT_ALLOWED);
        }
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String email = jwtUtil.extractEmail(token.substring(7));
        Optional<User> optionalUser = userRepository.findByEmail(email);
        logger.info("optionalUser: " + optionalUser);
        Map<String, Object> response = new HashMap<>();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String nickname = user.getNickname();
            userRepository.delete(user);
            response.put("status", 200);
            response.put("success", String.format("%s님 회원탈퇴에 성공하였습니다.", nickname));
        } else {
            throw new RestApiException(CustomUserErrorCode.USER_NOT_FOUND);
        }
        return response;
    }
}
