package com.post.prac.application.member.output;

import lombok.Builder;

@Builder
public record LoginDto(
		String memberId,
		String password,
		String name,
		String deleteYn
) {
}
