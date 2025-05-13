package com.example.barointern.Domain.Repository;

import com.example.barointern.Entity.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long{
}
