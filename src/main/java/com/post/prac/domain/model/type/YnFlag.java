package com.post.prac.domain.model.type;

import lombok.Getter;

@Getter
public enum YnFlag {
	Y("YES"),
	N("NO");

	private final String description;

	YnFlag(String description) {
		this.description = description;
	}
}
