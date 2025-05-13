package com.example.barointern.Filter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtTokenService {

    private final JwtUtil jwtUtil;
    private final UserDetailsService authService;
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
}
