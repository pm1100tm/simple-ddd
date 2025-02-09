package com.post.prac.presentation.response;

import com.post.prac.application.member.port.TokenInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponse {

	private String accessToken;

	private String refreshToken;

	public static LoginResponse of(TokenInfo tokenInfo) {
		return LoginResponse.builder()
				.accessToken(tokenInfo.accessToken())
				.refreshToken(tokenInfo.refreshToken())
				.build();
	}
}
