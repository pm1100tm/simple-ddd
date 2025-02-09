package com.post.prac.infrastructure.persistence;

import com.post.prac.domain.model.member.Member;
import com.post.prac.domain.repository.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, String>, MemberRepository {
}
