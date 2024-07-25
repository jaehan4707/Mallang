package com.chill.mallang.domain.user.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomOAuthToken extends AbstractAuthenticationToken {
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuthToken.class);
    private final Object principal;
    private String token;

    // 이메일과 토큰을 인자로 받는 생성자 추가
    public CustomOAuthToken(String email, String token) {
        super(null);
        this.principal = email;
        this.token = token;
        setAuthenticated(false);
    }

    // 사용자 정보와 권한을 인자로 받는 생성자
    public CustomOAuthToken(Object principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = token;
        logger.info("커스텀 토근 :"+ token);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}


