package com.chill.mallang.domain.user.test;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class GoogleOAuthTokenTest{


    private static final String CLIENT_ID = "187498583785-igo9tp8mbt29vnhg46s9v68frrefenhk.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "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";
    private static final String REDIRECT_URI = "http://localhost:8081";
    private static final String AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";
    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";

    public static void main(String[] args) throws IOException, URISyntaxException {
        // Step 1: 사용자로부터 인증 코드를 얻기 위한 URL
        String authUrl = AUTH_URL + "?scope=email%20profile&access_type=offline&include_granted_scopes=true&response_type=code"
                + "&redirect_uri=" + REDIRECT_URI
                + "&client_id=" + CLIENT_ID;

        // 브라우저를 열어 사용자에게 URL로 이동하게 합니다.
        if (Desktop.isDesktopSupported()) {
            Desktop.getDesktop().browse(new URI(authUrl));
        } else {
            System.out.println("Please go to this URL and authorize the application:");
            System.out.println(authUrl);
        }

        // Step 2: 사용자가 인증 후 리다이렉트된 URL에서 코드를 입력하도록 요청
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the authorization code: ");
        String authorizationCode = scanner.nextLine();

        // Step 3: 토큰을 얻기 위해 POST 요청을 보냄
        String idToken = getIdToken(authorizationCode);
        if (idToken != null) {
            System.out.println("ID Token: " + idToken);
        } else {
            System.out.println("Failed to retrieve token");
        }
    }

    private static String getIdToken(String authorizationCode) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(TOKEN_URL);

        String requestBody = "code=" + authorizationCode
                + "&client_id=" + CLIENT_ID
                + "&client_secret=" + CLIENT_SECRET
                + "&redirect_uri=" + REDIRECT_URI
                + "&grant_type=authorization_code";
        httpPost.setEntity(new StringEntity(requestBody));
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            String responseBody = EntityUtils.toString(response.getEntity());
            JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
            return jsonObject.has("id_token") ? jsonObject.get("id_token").getAsString() : null;
        }
    }
}
