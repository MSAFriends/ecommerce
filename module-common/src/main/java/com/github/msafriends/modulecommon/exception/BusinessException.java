package com.github.msafriends.modulecommon.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private final ErrorCode errorCode;
	private final String detail;

	public BusinessException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.detail = errorCode.getMessage();
	}

	public BusinessException(ErrorCode errorCode, String detail) {
		this.errorCode = errorCode;
		this.detail = String.format("%s (%s)", errorCode.getMessage(), detail);
	}
}
