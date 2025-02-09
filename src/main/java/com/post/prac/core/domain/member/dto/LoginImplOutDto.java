package com.post.prac.core.domain.member.dto;

import lombok.Builder;

@Builder
public record LoginImplOutDto(
		String accessToken,
		String refreshToken
) {
}
