package com.example.barointern.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration

public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        // JWT 인증 방식 설정
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Authorization");

        return new OpenAPI()
                .info(new Info()
                        .title("회원 인증 API")
                        .description("회원가입, 로그인 및 관리자 전용 API 명세서입니다.")
                        .version("v1.0")
                        .contact(new Contact().name("관리자").email("admin@example.com"))
                )
                .addSecurityItem(securityRequirement)
                .components(new Components().addSecuritySchemes("Authorization", securityScheme));
    }
}
