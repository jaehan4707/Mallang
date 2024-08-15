package com.chill.mallang.domain.user.service;

import com.chill.mallang.domain.user.dto.CustomUserDetails;
import com.chill.mallang.domain.user.model.User;
import com.chill.mallang.domain.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//로그인 시 회원 조회
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("유저디테일서비스유저 :"+email);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            logger.error("User not found with email: " + email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        User user = optionalUser.get();
        logger.info("유저디테일서비스유저 :" + user.toString());
        return new CustomUserDetails(user);
    }
}