package com.example.barointern.Domain.Service;

import com.example.barointern.Domain.Dto.LoginResponseDto;
import com.example.barointern.Domain.Repository.MemberRepository;
import com.example.barointern.Entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public LoginResponseDto signup(String username, String password, String nickname){
        Member member = new Member(username, password, nickname);
        Member savedMember = memberRepository.save(member);
        return new LoginResponseDto(savedMember.getUsername(), savedMember.getPassword(), savedMember.getNickname());
    }
}
