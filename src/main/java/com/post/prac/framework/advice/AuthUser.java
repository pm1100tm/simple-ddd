package com.post.prac.framework.advice;

import lombok.Builder;

@Builder
public record AuthUser(
		String memberId,
		String name
) {
}
