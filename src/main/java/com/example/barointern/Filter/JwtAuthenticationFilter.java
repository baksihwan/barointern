package com.example.barointern.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtTokenService.resolveTokenFromRequest(request.getHeader("Authorization"));

        if (!StringUtils.hasText(token) || !jwtTokenService.validateToken(token)) {
            log.info("유효하지 않은 인증 토큰입니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 상태 코드 설정
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"message\": \"유효하지 않은 인증 토큰입니다.\"}");
            return;
        }

        // 토큰이 유효하다면 다음 필터로 요청을 전달
        Authentication authentication = jwtTokenService.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}