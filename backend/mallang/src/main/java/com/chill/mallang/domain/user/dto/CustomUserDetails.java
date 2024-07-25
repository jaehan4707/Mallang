package com.chill.mallang.domain.user.dto;

import com.chill.mallang.domain.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자 권한을 반환합니다. 필요에 따라 구현하세요.
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {

            @Override
            public String getAuthority() {

                return user.getRole();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {
        // password 필드가 없는 경우 적절히 반환할 값을 설정해야 합니다.
        return null; // 또는 기본 비밀번호 설정 (예: user.getPassword() if it exists)
    }

    @Override
    public String getUsername() {;
        // 이메일을 사용자 이름으로 사용할 수 있습니다;
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 사용자 계정이 만료되지 않았음을 반환
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 사용자 계정이 잠겨있지 않았음을 반환
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 사용자 자격 증명이 만료되지 않았음을 반환
    }

    @Override
    public boolean isEnabled() {
        return true; // 사용자 계정이 활성화되었음을 반환
    }

}

