package com.chill.mallang.domain.user.jwt;

import com.chill.mallang.domain.user.oauth.CustomOAuthToken;
import com.chill.mallang.domain.user.service.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public LoginFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, JWTUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        Map<String, String> credentials = new ObjectMapper().readValue(request.getInputStream(), HashMap.class);
        String idToken = credentials.get("token");
        return getAuthenticationManager().authenticate(new CustomOAuthToken(idToken));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String jwtToken = jwtUtil.createJwt(authResult.getName(), "ROLE_USER", 60 * 60 * 10L);
        response.setHeader("Authentication", "Bearer " + jwtToken);
    }
}
