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
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private static final Logger logger = LoggerFactory.getLogger(JoinService.class);
    private final UserRepository userRepository;
    private final JWTUtil jwtUtil;

    //faction 추가
    @Autowired
    private FactionRepository factionRepository;

    public JoinService(UserRepository userRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public JoinResponseDTO joinProcess(JoinRequestDTO joinRequestDTO) {
        try {
            String email = joinRequestDTO.getEmail();
            String nickname = joinRequestDTO.getNickname();
            String picture = joinRequestDTO.getPicture();

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
            data.setTry_count(3);
            data.setFaction(faction);
            data.setRole("ROLE_USER");
            userRepository.save(data);

            String is_registered = userRepository.existsByEmail(email)? "true" : "false";

            // 가입한 유저 정보를 JoinResponseDTO로 변환
            JoinResponseDTO joinResponseDTO = new JoinResponseDTO();
            joinResponseDTO.setIs_registered(is_registered);
            return joinResponseDTO;
        } catch (Exception e) {
            throw new RuntimeException("회원가입 중 오류가 발생했습니다.", e);
        }
    }
}
