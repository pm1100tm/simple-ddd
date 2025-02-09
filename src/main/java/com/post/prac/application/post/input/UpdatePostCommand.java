package com.post.prac.application.post.input;

import lombok.Builder;

@Builder
public record UpdatePostCommand(
		String postId,
		String title,
		String content
) {
}
