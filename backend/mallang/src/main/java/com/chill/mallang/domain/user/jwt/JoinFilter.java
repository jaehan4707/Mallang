package com.chill.mallang.domain.user.jwt;

import com.chill.mallang.domain.user.dto.JoinRequestDTO;
import com.chill.mallang.domain.user.oauth.CustomOAuthToken;
import com.chill.mallang.domain.user.service.CustomUserDetailsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JoinFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger logger = LoggerFactory.getLogger(JoinFilter.class);

    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JoinFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, JWTUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        super(defaultFilterProcessesUrl);
        logger.info(defaultFilterProcessesUrl);
        setAuthenticationManager(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        logger.info(request.toString());
        JoinRequestDTO joinRequest = new ObjectMapper().readValue(request.getInputStream(), new TypeReference<JoinRequestDTO>(){});
        String idToken = joinRequest.getToken();
        return getAuthenticationManager().authenticate(new CustomOAuthToken(idToken));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        long secondsInAYear = 365L * 24 * 60 * 60;
        long tokenValidityInSeconds = 150L * secondsInAYear;
        String jwtToken = jwtUtil.createJwt(authResult.getName(), "ROLE_USER", tokenValidityInSeconds);
        response.setHeader("Authorization", "Bearer " + jwtToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication failed: " + failed.getMessage());
    }
}

