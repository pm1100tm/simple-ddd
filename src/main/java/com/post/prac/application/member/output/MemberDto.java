package com.post.prac.application.member.output;

import lombok.Builder;

@Builder
public record MemberDto(
		String memberId,
		String name,
		String deleteYn
) {
}
