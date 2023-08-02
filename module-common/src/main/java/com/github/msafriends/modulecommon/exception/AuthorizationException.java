package com.github.msafriends.modulecommon.exception;

public class AuthorizationException extends BusinessException {
	public AuthorizationException(ErrorCode errorCode) {
		super(errorCode);
	}

	public AuthorizationException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
