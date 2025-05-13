package com.example.barointern.Domain.Controller;

import com.example.barointern.Domain.Service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sun.beans.introspect.PropertyInfo.Name.description;

@RestController
@RequiredArgsConstructor
@RequestMapping("0.0.0.0:8080")
@Tag(name = "회원 API", description = "회원 관련 API입니다.")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
}
