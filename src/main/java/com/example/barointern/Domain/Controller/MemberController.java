package com.example.barointern.Domain.Controller;

import com.example.barointern.Domain.Dto.LoginResponseDto;
import com.example.barointern.Domain.Dto.SignUpRequestDto;
import com.example.barointern.Domain.Repository.MemberRepository;
import com.example.barointern.Domain.Service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("0.0.0.0:8080")
@Tag(name = "회원 API", description = "회원 관련 API입니다.")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    @SecurityRequirement(name = "Authorization")
    @Operation(summary = "회원가입", description = "회원가입을 처리합니다.")
    public ResponseEntity<LoginResponseDto> signup(@RequestBody SignUpRequestDto requestDto){
        LoginResponseDto loginResponseDto = memberService.signup(requestDto.getUsername(),
                                                                requestDto.getPassword(),
                                                                requestDto.getNickname());
        return ResponseEntity.ok(loginResponseDto);
    }
}
