package com.example.barointern.Filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JwtTokenService {

    private final JwtUtil jwtUtil;
    private final UserDetailsService authService;
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    // 토큰 유효 시간 (예: 1시간)
    private final long validityInMilliseconds = 3600000; // 1시간

    // 비밀 키 (Base64 인코딩된 문자열로 설정 권장)
    private final String secretKey = "yourSecretKey"; // 실제 서비스에서는 환경 변수로 관리 추천

    public String createToken(String username, List<String> roles) { // 사용자 권한 정보
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);  // 역할 정보 포함

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 토큰 추출
    public String resolveTokenFromRequest(String header) {
        if (header != null && header.startsWith(TOKEN_PREFIX)) {
            return header.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        return token != null && token.startsWith(TOKEN_PREFIX);
    }

    public boolean isAccessTokenDenied(String token) {
        return token != null && token.startsWith(TOKEN_HEADER);
    }


    public Authentication getAuthentication(String token){
        String username = jwtUtil.getUsername(token);
        UserDetails userDetails = authService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }
}