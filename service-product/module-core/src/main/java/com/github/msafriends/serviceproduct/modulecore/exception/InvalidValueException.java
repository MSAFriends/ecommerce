package com.github.msafriends.serviceproduct.modulecore.exception;

public class InvalidValueException extends BusinessException {
	public InvalidValueException(ErrorCode errorCode) {
		super(errorCode);
	}

	public InvalidValueException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
