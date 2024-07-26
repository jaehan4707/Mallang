package com.chill.mallang.config;

import com.chill.mallang.domain.user.jwt.JWTFilter;
import com.chill.mallang.domain.user.jwt.JWTUtil;
import com.chill.mallang.domain.user.jwt.LoginFilter;
import com.chill.mallang.domain.user.oauth.CustomOAuthProvider;
import com.chill.mallang.domain.user.oauth.GoogleOAuthService;
import com.chill.mallang.domain.user.service.CustomUserDetailsService;
import com.chill.mallang.domain.user.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final GoogleOAuthService googleOAuthService;
    private final JWTFilter jwtFilter;
    private final JWTUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;
    private final CustomOAuthProvider customOAuthProvider;
    private final JoinService joinService;

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(GoogleOAuthService googleOAuthService, JWTFilter jwtFilter, JWTUtil jwtUtil, CustomUserDetailsService userDetailsService, CustomOAuthProvider customOAuthProvider, JoinService joinService) {
        this.googleOAuthService = googleOAuthService;
        this.jwtFilter = jwtFilter;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.customOAuthProvider = customOAuthProvider;
        this.joinService = joinService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 로그인과 회원가입 요청에 대해서만 LoginFilter와 JoinFilter가 먼저 동작하도록 설정
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("api/v1/user/login", "api/v1/user/join").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(new LoginFilter("/api/v1/user/login", authenticationManager(authenticationConfiguration), jwtUtil, userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customOAuthProvider);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers ("/","/swagger-ui/**", "/api-docs/**");
    }
}
