package com.chill.mallang.domain.user.oauth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomOAuthToken extends AbstractAuthenticationToken {

    private final String idToken;

    public CustomOAuthToken(String idToken) {
        super(null);
        this.idToken = idToken;
        setAuthenticated(false);
    }

    public CustomOAuthToken(Collection<? extends GrantedAuthority> authorities, String idToken) {
        super(authorities);
        this.idToken = idToken;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return idToken;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}