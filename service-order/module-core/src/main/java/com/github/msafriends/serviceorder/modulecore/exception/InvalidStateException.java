package com.github.msafriends.serviceorder.modulecore.exception;

public class InvalidStateException extends BusinessException {
    public InvalidStateException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidStateException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }
}
