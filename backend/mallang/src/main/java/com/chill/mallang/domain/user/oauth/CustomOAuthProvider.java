package com.chill.mallang.domain.user.oauth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

@Component
public class CustomOAuthProvider implements AuthenticationProvider {

    private final GoogleOAuthService googleOAuthService;

    public CustomOAuthProvider(GoogleOAuthService googleOAuthService) {
        this.googleOAuthService = googleOAuthService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String idToken = (String) authentication.getCredentials();
        String email = authentication.getName();
        try {
            GoogleIdToken.Payload payload = googleOAuthService.verifyToken(idToken);

            if (payload != null) {
                UserDetails userDetails = new User(payload.getSubject(), "", new ArrayList<>());
                return new CustomOAuthToken(userDetails.getAuthorities(), idToken, email);
            }

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Failed to verify token", e);
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomOAuthToken.class);
    }
}