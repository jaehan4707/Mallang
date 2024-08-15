package com.chill.mallang.domain.user.oauth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomOAuthToken extends AbstractAuthenticationToken {

    private final String idToken;
    private final String email;  // 이메일 필드 추가

    public CustomOAuthToken(String idToken, String email) {
        super(null);
        this.idToken = idToken;
        this.email = email;
        setAuthenticated(false);
    }

    public CustomOAuthToken(Collection<? extends GrantedAuthority> authorities, String idToken, String email) {
        super(authorities);
        this.idToken = idToken;
        this.email = email;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return idToken;
    }

    @Override
    public Object getPrincipal() {
        return email;  // 이메일 반환
    }
}