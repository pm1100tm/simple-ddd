package com.post.prac.domain.repository;

import com.post.prac.domain.model.member.Member;

import java.util.Optional;

public interface MemberRepository {
	Optional<Member> findByMemberId(String memberId);
}
