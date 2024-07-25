package com.chill.mallang.domain.user.service;

import com.chill.mallang.domain.user.dto.JoinDTO;
import com.chill.mallang.domain.user.dto.JoinResponseDTO;
import com.chill.mallang.domain.user.jwt.LoginFilter;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);
    private final UserRepository userRepository;
    public JoinService(UserRepository userRepository){

        this.userRepository = userRepository;
    }

    public JoinResponseDTO joinProcess(JoinDTO joinDTO){
        String email = joinDTO.getEmail();
        String nickname = joinDTO.getNickname();
//        Long faction_id = joinDTO.getFaction_id();
        String picture = joinDTO.getPicture();
        Integer try_acount = joinDTO.getTry_count();

        Boolean isExist = userRepository.existsByEmail(email);

        if(isExist){
            // 클라이언트에게 던져줄 로직짜기
            System.out.println("이메일 중복");
        }

        User data = new User();
        logger.info(data.toString());
        data.setEmail(email);
        data.setNickname(nickname);
//        data.setFaction_id(faction_id);
        data.setPicture(picture);
        data.setTry_count(try_acount);
        data.setRole("ROLE_USER");
        System.out.println(data);
        userRepository.save(data);

        // 가입한 유저 정보를 JoinResponseDTO로 변환
        JoinResponseDTO joinResponseDTO = new JoinResponseDTO();
        joinResponseDTO.setEmail(data.getEmail());
        joinResponseDTO.setNickname(data.getNickname());
        joinResponseDTO.setPicture(data.getPicture());
        joinResponseDTO.setRole(data.getRole());
        joinResponseDTO.setTry_count(data.getTry_count());
        return joinResponseDTO;
    }
}
