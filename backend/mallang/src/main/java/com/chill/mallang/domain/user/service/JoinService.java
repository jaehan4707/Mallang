package com.chill.mallang.domain.user.service;

import com.chill.mallang.domain.user.dto.JoinDTO;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    private final UserRepository userRepository;

    public JoinService(UserRepository userRepository){

        this.userRepository = userRepository;
    }

    public void joinProcess(JoinDTO joinDTO){
        String email = joinDTO.getEmail();
        String nickname = joinDTO.getNickname();
//        Long faction_id = joinDTO.getFaction_id();
        String picture = joinDTO.getPicture();
        Integer try_acount = joinDTO.getTry_count();


        Boolean isExist = userRepository.existsByEmail(email);

        if(isExist){
            // 클라이언트에게 던져줄 로직짜기
            System.out.println("이메일 중복");
            return;
        }

        User data = new User();
        data.setEmail(email);
        data.setNickname(nickname);
//        data.setFaction_id(faction_id);
        data.setPicture(picture);
        data.setTry_count(try_acount);
        data.setRole("ROLE_USER");
        System.out.println(data);
        userRepository.save(data);
    }
}
