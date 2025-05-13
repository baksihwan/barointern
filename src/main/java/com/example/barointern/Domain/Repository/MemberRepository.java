package com.example.barointern.Domain.Repository;

import com.example.barointern.Entity.Member;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long{
}
