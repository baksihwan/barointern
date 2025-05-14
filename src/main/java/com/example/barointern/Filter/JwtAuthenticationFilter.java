package com.example.barointern.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
// JWT 토큰을 검사해서, 로그인 없이 인증해 주는 필터
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService authService;
    private final JwtTokenService jwtTokenService;

    public JwtAuthenticationFilter(UserDetailsService authService, JwtTokenService jwtTokenService) {
        this.authService = authService;
        this.jwtTokenService = jwtTokenService;
    }

    public String getUsername(String token){
        return token.substring(7);
    }
    // 토큰을 바탕으로 SecurityContext에 인증 정보를 심는 작업을 한다.
    public Authentication getAuthentication(String token){
        String username = this.getUsername(token);
        UserDetails userDetails = authService.loadUserByUsername(username);
        Authentication auth  = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

    return auth;}

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //토큰 파싱
        String token = jwtTokenService.resolveTokenFromRequest(request.getHeader("Authorization"));

        //토큰 문제 유무 체크
        if(StringUtils.hasText(token) && jwtTokenService.validateToken(token)
                && !jwtTokenService.isAccessTokenDenied(token)) {
            //토큰 유효성 검증시 로직
            Authentication auth = jwtTokenService.getAuthentication(token); //Authenticaton 객체
            SecurityContextHolder.getContext().setAuthentication(auth); // SecurityContext에 로드
        } else {
            log.info("토큰 유효성 검증을 실패하였습니다.");
        }
        filterChain.doFilter(request, response);
    }
    }

