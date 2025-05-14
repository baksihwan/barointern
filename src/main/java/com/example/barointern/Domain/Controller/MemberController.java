package com.example.barointern.Domain.Controller;

import com.example.barointern.Domain.Dto.LoginResponseDto;
import com.example.barointern.Domain.Dto.SignUpRequestDto;
import com.example.barointern.Domain.Repository.MemberRepository;
import com.example.barointern.Domain.Service.MemberService;
import com.example.barointern.Entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("0.0.0.0:8080")
@Tag(name = "회원 API", description = "회원 관련 API입니다.")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    @SecurityRequirement(name = "Authorization") //Swagger를 위한 보안요건 명시
    @Operation(summary = "회원가입", description = "회원가입을 처리합니다.")
    public ResponseEntity<LoginResponseDto> signup(@RequestBody SignUpRequestDto requestDto){
        LoginResponseDto loginResponseDto = memberService.signup(requestDto.getUsername(),
                                                                requestDto.getPassword(),
                                                                requestDto.getNickname());
        return ResponseEntity.ok(loginResponseDto);
    }

    @GetMapping("/login")
    @SecurityRequirement(name = "Authorization") //Swagger를 위한 보안요건 명시
    @Operation(summary = "로그인", description = "JWT 인증된 사용자의 정보를 반환합니다.")
    public ResponseEntity<Member> login(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Member member = memberRepository.findByUsername(username);
        return ResponseEntity.ok(member);
    }

    @GetMapping("/admin/login")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> admin(){
        return ResponseEntity.ok("관리자 전용입니다.");
    }
}
