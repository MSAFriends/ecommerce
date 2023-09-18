package com.github.msafriends.serviceproduct.modulecore.exception;

public class FileProcessingException extends BusinessException{
    public FileProcessingException(ErrorCode errorCode) {
        super(errorCode);
    }

    public FileProcessingException(ErrorCode errorCode, Object... args) {
        super(errorCode, args);
    }
}
