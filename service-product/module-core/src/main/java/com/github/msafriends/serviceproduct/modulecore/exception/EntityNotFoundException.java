package com.github.msafriends.serviceproduct.modulecore.exception;

public class EntityNotFoundException extends BusinessException {
	public EntityNotFoundException(ErrorCode errorCode) {
		super(errorCode);
	}

	public EntityNotFoundException(ErrorCode errorCode, Object... args) {
		super(errorCode, args);
	}
}
