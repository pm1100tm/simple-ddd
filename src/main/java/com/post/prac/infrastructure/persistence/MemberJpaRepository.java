package com.post.prac.infrastructure.persistence;

import com.post.prac.core.domain.member.Member;
import com.post.prac.core.repository.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, String>, MemberRepository {
}
