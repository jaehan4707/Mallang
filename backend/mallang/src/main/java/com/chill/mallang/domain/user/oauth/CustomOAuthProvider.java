package com.chill.mallang.domain.user.oauth;

import com.chill.mallang.domain.user.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomOAuthProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuthProvider.class);
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private GoogleOAuthService googleOAuthService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        logger.info("프로바이더 :"+token);

        try {
            JsonNode userInfo = googleOAuthService.getUserInfo(token);
            logger.info("userInfo :"+userInfo);
            String email = userInfo.get("email").asText();
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            return new CustomOAuthToken(userDetails, token, userDetails.getAuthorities());
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid OAuth token", e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomOAuthToken.class.isAssignableFrom(authentication);
    }
}