package com.chill.mallang.domain.user.oauth;

import com.chill.mallang.domain.user.controller.v1.JoinController;
import com.chill.mallang.errors.errorcode.CustomErrorCode;
import com.chill.mallang.errors.exception.RestApiException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(CustomOAuthProvider.class);

    private final GoogleOAuthService googleOAuthService;

    public CustomOAuthProvider(GoogleOAuthService googleOAuthService) {
        this.googleOAuthService = googleOAuthService;
    }

    // 에러 try catch fail. 대신에 log 남김.
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String idToken = (String) authentication.getCredentials();
        String email = authentication.getName();
        try {
            GoogleIdToken.Payload payload = googleOAuthService.verifyToken(idToken);
            if (payload == null) {
                logger.info("GoogleID)token invalid"+payload.toString());
                throw new RestApiException(CustomErrorCode.INVALID_ID_TOKEN);
            }

            String googleEmail = payload.getEmail();

            if (email.equals(googleEmail)) {
                logger.info("oauth authenticated success"+email);
                UserDetails userDetails = new User(payload.getSubject(), "", new ArrayList<>());
                return new CustomOAuthToken(userDetails.getAuthorities(), idToken, email);
            } else {
                logger.info("email is not matched"+email);
                throw new RestApiException(CustomErrorCode.EMAIL_NOT_MATCHED);
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new RestApiException(CustomErrorCode.EMAIL_NOT_MATCHED);
        }
    }


    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomOAuthToken.class);
    }
}