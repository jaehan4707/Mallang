package com.chill.mallang.domain.user.service;

import com.chill.mallang.domain.faction.model.Faction;
import com.chill.mallang.domain.faction.repository.FactionRepository;
import com.chill.mallang.domain.user.dto.JoinRequestDTO;
import com.chill.mallang.domain.user.dto.JoinResponseDTO;
import com.chill.mallang.domain.user.errors.CustomUserErrorCode;
import com.chill.mallang.domain.user.jwt.JWTUtil;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import com.chill.mallang.errors.errorcode.CustomErrorCode;
import com.chill.mallang.errors.exception.RestApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class JoinService {

    private static final Logger logger = LoggerFactory.getLogger(JoinService.class);
    private final UserRepository userRepository;
    private final FactionRepository factionRepository;
    private final JWTUtil jwtUtil;

    @Autowired
    public JoinService(UserRepository userRepository, FactionRepository factionRepository, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.factionRepository = factionRepository;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, Object> joinProcess(JoinRequestDTO joinRequestDTO, String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                throw new RestApiException(CustomErrorCode.INVALID_PARAMETER);
            }

            String tokenEmail = jwtUtil.extractEmail(token.substring(7));
            String email = joinRequestDTO.getEmail();

            if (!tokenEmail.equals(email)){
                throw new RestApiException(CustomUserErrorCode.EMAIL_NOT_MATCHED);
            }
            String nickname = joinRequestDTO.getNickname();
            String picture = joinRequestDTO.getPicture();

            // Faction 추가
            Faction faction = factionRepository.findByName(joinRequestDTO.getFaction())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid faction"));

            // 이메일 중복 확인
            boolean isExist = userRepository.existsByEmail(email);
            if (isExist) {
                throw new RestApiException(CustomUserErrorCode.EMAIL_IS_EXISTS);
            }
            if(userRepository.existsByNickname(nickname)){
                throw new RestApiException(CustomUserErrorCode.NICKNAME_IS_EXISTS);
            }

            User user = new User();
            logger.info(user.toString());
            user.setEmail(email);
            user.setNickname(nickname);
            user.setPicture(picture);
            user.setTry_count(3);
            user.setFaction(faction);
            user.setRole("ROLE_USER");
            userRepository.save(user);

            String is_registered = userRepository.existsByEmail(email) ? "true" : "false";

            // 가입한 유저 정보를 JoinResponseDTO로 변환
            JoinResponseDTO joinResponseDTO = new JoinResponseDTO();
            joinResponseDTO.setIs_registered(is_registered);
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("token", token.substring(7));
            dataMap.put("is_registered", joinResponseDTO.getIs_registered());
            response.put("data", dataMap);
        } catch (Exception e) {
            logger.error("회원가입 중 오류 발생", e);
            throw new RestApiException(CustomUserErrorCode.JOIN_IS_FAILED);
        }
        return response;
    }
}
