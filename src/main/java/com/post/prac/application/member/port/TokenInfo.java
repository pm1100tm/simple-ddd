package com.post.prac.application.member.port;

import lombok.Builder;

@Builder
public record TokenInfo(
		String accessToken,
		String refreshToken
) {
}
