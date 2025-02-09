package com.post.prac.core.domain.member.dto;

import lombok.Builder;

@Builder
public record LoginImplInDto(
		String memberId,
		String password,
		String name,
		String deleteYn
) {
}
