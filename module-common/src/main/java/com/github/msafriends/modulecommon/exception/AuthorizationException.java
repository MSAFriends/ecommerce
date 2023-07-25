package com.github.msafriends.modulecommon.exception;

public class AuthorizationException extends BusinessException{
	public AuthorizationException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	public AuthorizationException(ErrorCode errorCode) {
		super(errorCode);
	}
}