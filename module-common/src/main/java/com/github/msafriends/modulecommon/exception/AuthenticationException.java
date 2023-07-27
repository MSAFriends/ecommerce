package com.github.msafriends.modulecommon.exception;

public class AuthenticationException extends BusinessException {
	public AuthenticationException(ErrorCode errorCode) {
		super(errorCode);
	}

	public AuthenticationException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}