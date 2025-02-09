package com.post.prac.application.member.input;

import lombok.Builder;

@Builder
public record LoginCommand(
		String memberId,
		String password
) {
}
