package com.post.prac.framework.advice;

import lombok.Builder;

@Builder
public record LoginUser(
		String memberId,
		String name
) {
}
