package com.post.prac.application.post.input;

import lombok.Builder;

@Builder
public record CreatePostCommand(
		String title,
		String content
) {
}
