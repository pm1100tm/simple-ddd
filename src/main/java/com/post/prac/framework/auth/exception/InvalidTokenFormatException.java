package com.post.prac.framework.auth.exception;

public class InvalidTokenFormatException extends RuntimeException{
	private static final String DEFAULT_MESSAGE = "잘못된 토큰 형식입니다.";
	public InvalidTokenFormatException() {
		super(DEFAULT_MESSAGE);
	}
}
