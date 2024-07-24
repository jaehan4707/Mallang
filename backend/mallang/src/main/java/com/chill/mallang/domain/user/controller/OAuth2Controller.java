package com.chill.mallang.domain.user.controller;
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

//    @Value("${spring.security.oauth2.client.registration.google.client-id}")
//    private String clientId;
//
//    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
//    private String clientSecret;
//
//    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
//    private String redirectUri;
//
//    @GetMapping("/login/oauth2/code/google")
//    public String oauth2Callback(@RequestParam(name = OAuth2ParameterNames.CODE) String code) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        Map<String, String> params = new HashMap<>();
//        params.put("client_id", clientId);
//        params.put("client_secret", clientSecret);
//        params.put("code", code);
//        params.put("redirect_uri", redirectUri);
//        params.put("grant_type", "authorization_code");
//
//        Map<String, Object> response = restTemplate.postForObject("https://oauth2.googleapis.com/token", params, Map.class);
//        String accessToken = (String) response.get("access_token");
//
//        // accessToken을 사용하여 사용자 정보를 가져오거나 추가 작업 수행
//        // 예: restTemplate.getForObject("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + accessToken, Map.class);
//        return "redirect:/api/v1/user/home";
//    }
}