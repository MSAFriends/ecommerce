package com.github.msafriends.modulecommon.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
	private final ErrorCode errorCode;
	private final String detail;

	public BusinessException(ErrorCode errorCode, String detail) {
		this.errorCode = errorCode;
		this.detail = detail;
	}

	public BusinessException(ErrorCode errorCode) {
		this.errorCode = errorCode;
		this.detail = errorCode.getMessage();
	}

}
