package com.chill.mallang.domain.user.service;

import com.chill.mallang.domain.faction.model.Faction;
import com.chill.mallang.domain.faction.repository.FactionRepository;
import com.chill.mallang.domain.user.dto.JoinRequestDTO;
import com.chill.mallang.domain.user.dto.JoinResponseDTO;
import com.chill.mallang.domain.user.jwt.JWTUtil;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.oauth.GoogleOAuthService;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private static final Logger logger = LoggerFactory.getLogger(JoinService.class);
    private final UserRepository userRepository;
    private final GoogleOAuthService googleOAuthService;
    private final JWTUtil jwtUtil;

    //faction 추가
    @Autowired
    private FactionRepository factionRepository;

    public JoinService(UserRepository userRepository, GoogleOAuthService googleOAuthService, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.googleOAuthService = googleOAuthService;
        this.jwtUtil = jwtUtil;
    }

    public JoinResponseDTO joinProcess(JoinRequestDTO joinRequestDTO) {
        try {
            GoogleIdToken.Payload payload = googleOAuthService.verifyToken(joinRequestDTO.getToken());
            logger.info("GoogleIdToken.Payload :"+payload);
            if (payload == null) {
                throw new IllegalArgumentException("유효하지 않은 ID 토큰입니다.");
            }

            String email = payload.getEmail();
            String nickname = joinRequestDTO.getNickname();
            String picture = joinRequestDTO.getPicture();
            Integer try_count = joinRequestDTO.getTry_count();

            //faction 추가
            Faction faction = factionRepository.findByName(joinRequestDTO.getFaction())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid faction"));

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
            data.setFaction(faction);
            data.setRole("ROLE_USER");
            userRepository.save(data);

            // 가입한 유저 정보를 JoinResponseDTO로 변환
            long secondsInAYear = 365L * 24 * 60 * 60;
            long tokenValidityInSeconds = 150L * secondsInAYear;
            String jwtToken = jwtUtil.createJwt(email, "ROLE_USER", tokenValidityInSeconds);

            // 가입한 유저 정보를 JoinResponseDTO로 변환
            JoinResponseDTO joinResponseDTO = new JoinResponseDTO();
            joinResponseDTO.setJwtToken(jwtToken); // JWT 토큰 설정
            return joinResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("회원가입 중 오류가 발생했습니다.", e);
        }
    }
}
