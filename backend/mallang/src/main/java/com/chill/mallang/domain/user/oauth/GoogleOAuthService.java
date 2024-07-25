package com.chill.mallang.domain.user.oauth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleOAuthService {

    @Value("${google.client.id}")
    private String clientId; // 구글 클라이언트 ID

    /**
     * ID 토큰을 검증하고 페이로드를 반환합니다.
     *
     * @param idTokenString 클라이언트로부터 받은 ID 토큰 문자열
     * @return ID 토큰이 유효하면 페이로드를 반환하고, 그렇지 않으면 null을 반환
     * @throws GeneralSecurityException 토큰 검증 중 발생하는 보안 예외
     * @throws IOException 토큰 검증 중 발생하는 입출력 예외
     */
    public GoogleIdToken.Payload verifyToken(String idTokenString) throws GeneralSecurityException, IOException {
        // GoogleIdTokenVerifier 객체 생성
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();

        // ID 토큰 검증
        GoogleIdToken idToken = verifier.verify(idTokenString);

        // 유효한 토큰일 경우 페이로드 반환, 그렇지 않으면 null 반환
        return idToken != null ? idToken.getPayload() : null;
    }
}
