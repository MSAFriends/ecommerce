package com.github.msafriends.modulecommon.exception;

public class InvalidValueException extends BusinessException {
	public InvalidValueException(ErrorCode errorCode) {
		super(errorCode);
	}

	public InvalidValueException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
