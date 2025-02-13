package com.post.prac.framework.auth.exception;

public class InvalidTokenException extends RuntimeException{
	private static final String DEFAULT_MESSAGE = "잘못된 토큰입니다.";
	public InvalidTokenException() {
		super(DEFAULT_MESSAGE);
	}
	public InvalidTokenException(String msg) {
		super(msg);
	}
}
