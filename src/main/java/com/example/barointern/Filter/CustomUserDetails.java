package com.example.barointern.Filter;

import com.example.barointern.Entity.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

// 내가 원하는 방식으로 사용자 정보를 설정하는 클래스
public class CustomUserDetails implements UserDetails {

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한을 반환 (예: ROLE_USER, ROLE_ADMIN 등)
        return Collections.singletonList(new SimpleGrantedAuthority(member.getRole()));
    }

    @Override
    public String getPassword() {
        return member.getPassword(); // JWT에서는 인증 후에만 사용됨
    }

    @Override
    public String getUsername() {
        return member.getEmail(); // JWT의 subject와 일치하게 설계
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠김 여부
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 인증 정보 만료 여부
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정 활성화 여부
    }

    public Member getMember() { // 멤버 생성자
        return member;
    }
}