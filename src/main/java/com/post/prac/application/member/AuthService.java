package com.post.prac.application.member;

import com.post.prac.application.member.input.LoginCommand;
import com.post.prac.application.member.output.LoginDto;
import com.post.prac.application.member.port.LoginOutputPort;
import com.post.prac.application.member.port.TokenInfo;
import com.post.prac.core.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;

	private final LoginOutputPort loginOutputPort;

	public TokenInfo process(LoginCommand command) {
		var member = memberRepository.findByMemberId(command.memberId()).orElseThrow(
				() -> new IllegalArgumentException("Member not found : " + command.memberId()));

		var loginDto = LoginDto.builder()
				.memberId(member.memberId())
				.password(member.password())
				.name(member.name())
				.build();

		var tokenInfo = loginOutputPort.createToken(loginDto);

		return TokenInfo.builder()
				.accessToken(tokenInfo.accessToken())
				.refreshToken(tokenInfo.refreshToken())
				.build();
	}
}
