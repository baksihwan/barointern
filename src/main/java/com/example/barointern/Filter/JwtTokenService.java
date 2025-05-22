package com.example.barointern.Filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtTokenService { //인증 권한 부여

    private final JwtUtil jwtUtil;
    private final UserDetailsService authService;
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";


    public String createToken(String username) { // 사용자 권한 정보
        return jwtUtil.createToken(username);
    }

    // 토큰 추출
    public String resolveTokenFromRequest(String header) {
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            log.debug("Authorization Header : {}", header);
            return header.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    //Jwt 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String token){
        String username = jwtUtil.getUsername(token);
        if (username == null){
            return null;
        }
        UserDetails userDetails = authService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }
    //토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            return
                    jwtUtil.validateToken(token);
        }catch (Exception e) {
            log.warn("토큰 검증 실패 : {}", e.getMessage());
        } return false;
    }

    public String getUsername(String token){
        return jwtUtil.getUsername(token);
    }
}