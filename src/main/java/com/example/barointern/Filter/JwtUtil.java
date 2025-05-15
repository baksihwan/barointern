package com.example.barointern.Filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
//JWT를 생성하고 검증하며, 토큰 안의 정보를 파싱하는 도구 역할
// 1.토큰 생성  2.토큰 유효성 검증  3.Claims 파싱  4.사용자 정보 추출
public class JwtUtil {

    //비밀 키
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //토큰 유효 시간 (1시간)
    private final long EXPIRATION_TIME = 60 * 60 * 1000;

    //토큰 생성
    public String createToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    // 내부적으로 Claims 파싱하는 메서드
    private Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //유효성 검사
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token){
        return getClaims(token).getSubject();
    }
}