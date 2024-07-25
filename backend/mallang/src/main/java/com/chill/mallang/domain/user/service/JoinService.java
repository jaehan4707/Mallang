package com.chill.mallang.domain.user.service;

import com.chill.mallang.domain.user.dto.JoinRequestDTO;
import com.chill.mallang.domain.user.dto.JoinResponseDTO;
import com.chill.mallang.domain.user.jwt.JWTUtil;
import com.chill.mallang.domain.user.jwt.LoginFilter;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.oauth.GoogleOAuthService;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private static final Logger logger = LoggerFactory.getLogger(JoinService.class);
    private final UserRepository userRepository;
    private final GoogleOAuthService googleOAuthService;
    private final JWTUtil jwtUtil;

    public JoinService(UserRepository userRepository, GoogleOAuthService googleOAuthService, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.googleOAuthService = googleOAuthService;
        this.jwtUtil = jwtUtil;
    }

    public JoinResponseDTO joinProcess(JoinRequestDTO joinRequestDTO) {
        try {
            JsonNode userInfo = googleOAuthService.getUserInfo(joinRequestDTO.getToken());
            logger.info("userInfo :"+userInfo);
            String email = userInfo.get("email").asText();
            String nickname = joinRequestDTO.getNickname();
            String picture = joinRequestDTO.getPicture();
            Integer try_count = joinRequestDTO.getTry_count();

            // 이메일 중복 확인
            Boolean isExist = userRepository.existsByEmail(email);
            if (isExist) {
                throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
            }

            User data = new User();
            logger.info(data.toString());
            data.setEmail(email);
            data.setNickname(nickname);
            data.setPicture(picture);
            data.setTry_count(try_count);
            data.setRole("ROLE_USER");
            userRepository.save(data);

            // JWT 토큰 생성
            String jwtToken = jwtUtil.createJwt(email, "ROLE_USER", 60 * 60 * 10L);

            // 가입한 유저 정보를 JoinResponseDTO로 변환
            JoinResponseDTO joinResponseDTO = new JoinResponseDTO();
            joinResponseDTO.setEmail(data.getEmail());
            joinResponseDTO.setNickname(data.getNickname());
            joinResponseDTO.setPicture(data.getPicture());
            joinResponseDTO.setRole(data.getRole());
            joinResponseDTO.setTry_count(data.getTry_count());
            logger.info("jwt토큰 :"+jwtToken);

            return joinResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("회원가입 중 오류가 발생했습니다.", e);
        }
    }
}
