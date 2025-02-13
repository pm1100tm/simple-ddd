package com.post.prac.core.domain.member.impl;

import com.post.prac.application.member.output.LoginDto;
import com.post.prac.application.member.port.LoginOutputPort;
import com.post.prac.core.domain.member.dto.LoginImplInDto;
import com.post.prac.core.domain.member.dto.LoginImplOutDto;
import com.post.prac.framework.auth.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoginAdapter implements LoginOutputPort {

	private final JwtProvider provider;

	@Override
	public LoginImplOutDto createToken(LoginDto dto) {
		var implDto = LoginImplInDto.builder()
				.memberId(dto.memberId())
				.password(dto.password())
				.name(dto.name())
				.deleteYn(dto.deleteYn())
				.build();

		String acToken = provider.generateAcToken(implDto);
		String rfToken = provider.generateRfToken(implDto);

		return LoginImplOutDto.builder().accessToken(acToken).refreshToken(rfToken).build();
	}
}
