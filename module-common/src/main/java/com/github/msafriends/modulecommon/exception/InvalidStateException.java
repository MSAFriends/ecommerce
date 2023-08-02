package com.github.msafriends.modulecommon.exception;

public class InvalidStateException extends BusinessException {
    public InvalidStateException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidStateException(ErrorCode errorCode, String detail) {
        super(errorCode, detail);
    }
}
