package com.github.msafriends.modulecore.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String detail;

	public BusinessException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.detail = errorCode.getMessage();
	}

	public BusinessException(ErrorCode errorCode, Object...args) {
		this.errorCode = errorCode;
		this.detail = String.format(errorCode.getMessage(), args);
	}
}
