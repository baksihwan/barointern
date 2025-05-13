package com.example.barointern.Filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFIlter extends OncePerRequestFilter {

    private final UserDetailsService authService;
    private final JwtTokenService jwtTokenService;
}
