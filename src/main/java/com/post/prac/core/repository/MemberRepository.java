package com.post.prac.core.repository;

import com.post.prac.core.domain.member.Member;

import java.util.Optional;

public interface MemberRepository {
	Optional<Member> findByMemberId(String memberId);
}
