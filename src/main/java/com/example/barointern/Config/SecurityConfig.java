package com.example.barointern.Config;

import com.example.barointern.Filter.JwtAuthenticationFilter;
import com.example.barointern.Filter.JwtTokenService;
import com.example.barointern.Filter.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService authService;
    private final JwtTokenService jwtTokenService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("ADMIN")    // 관리자 권한 필요
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")  // 사용자 및 관리자 허용
                        .requestMatchers("/auth/**", "/v3/api-docs/**", "/swagger-ui/**").permitAll() // 로그인/회원가입 등
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(authService, jwtTokenService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

