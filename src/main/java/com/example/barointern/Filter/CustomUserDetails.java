package com.example.barointern.Filter;

import com.example.barointern.Entity.Member;

public class CustomUserDetails implements UserDetails{

    private final Member member;

    public CustomUserDetails(Member member) {
        this.member = member;
    }
}
