package com.post.prac.application.member;

import com.post.prac.application.member.input.LoginCommand;
import com.post.prac.application.member.output.MemberDto;
import com.post.prac.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;

	public MemberDto process(LoginCommand command) {
		var member = memberRepository.findByMemberId(command.memberId()).orElseThrow(
				() -> new IllegalArgumentException("Member not found : " + command.memberId()));

		return MemberDto.builder()
				.memberId(member.memberId())
				.name(member.name())
				.build();
	}
}
