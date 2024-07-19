package com.chill.mallang.domain.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class OAuth2Controller {
    private static final Logger logger = LoggerFactory.getLogger(OAuth2Controller.class);

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @GetMapping("/login/oauth2/code/google")
    public String oauth2Callback(@RequestParam(name = OAuth2ParameterNames.CODE) String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("code", code);
        params.put("redirect_uri", redirectUri);
        params.put("grant_type", "authorization_code");
        logger.debug("Requesting access token with params: {}", params);

        // Google로부터 토큰을 요청
        Map<String, Object> response = restTemplate.postForObject("https://oauth2.googleapis.com/token", params, Map.class);

        // Google 응답 데이터 로그 출력
        if (response != null) {
            logger.debug("Response from Google Token API: {}", response);

            if (response.containsKey("access_token")) {
                String accessToken = (String) response.get("access_token");
                logger.debug("Access Token: {}", accessToken);

                // Access Token을 사용하여 사용자 정보를 가져오기
                Map<String, Object> userInfo = restTemplate.getForObject("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken, Map.class);
                logger.debug("User Info: {}", userInfo);
            } else {
                logger.error("Access token not found in response.");
            }
        } else {
            logger.error("Failed to get a response from Google Token API.");
        }

        return "redirect:/api/v1/user/home";
    }
}