package com.example.barointern.Domain.Repository;

import com.example.barointern.Entity.Member;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    Optional<Member> findByEmail(String email);

    Member findByUsername(String username);
}
