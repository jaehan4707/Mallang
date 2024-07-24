package com.chill.mallang.domain.user.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

// 구글로 토큰 검정
@Service
public class GoogleOAuthService {
    private static final String TOKEN_INFO_URL = "https://oauth2.googleapis.com/tokeninfo?id_token=";

    @Value("${google.client.id}")
    private String clientId;

    public JsonNode getUserInfo(String idToken) throws Exception {
        return getUserInfoFromToken(idToken);
    }

    private JsonNode getUserInfoFromToken(String idToken) throws Exception {
        URI tokenInfoUri = new URI(TOKEN_INFO_URL + idToken);
        HttpURLConnection conn = (HttpURLConnection) tokenInfoUri.toURL().openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(content.toString());
        } else {
            throw new IllegalArgumentException("Failed to retrieve user info: " + responseCode);
        }
    }
}