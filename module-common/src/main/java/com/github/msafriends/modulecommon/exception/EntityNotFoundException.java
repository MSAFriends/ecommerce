package com.github.msafriends.modulecommon.exception;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

	public EntityNotFoundException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}
}
