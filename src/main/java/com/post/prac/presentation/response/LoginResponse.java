package com.post.prac.presentation.response;

import com.post.prac.application.member.port.TokenInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponse {

	@Schema(description = "엑세스 토큰")
	private String accessToken;

	@Schema(description = "리프레시 토큰")
	private String refreshToken;

	public static LoginResponse of(TokenInfo tokenInfo) {
		return LoginResponse.builder()
				.accessToken(tokenInfo.accessToken())
				.refreshToken(tokenInfo.refreshToken())
				.build();
	}
}
