package com.github.msafriends.modulecommon.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.github.msafriends.modulecommon.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class RestApiExceptionHandler {
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
		ErrorResponse response = ErrorResponse.of(ErrorCode.AUTHENTICATION_FAILED);
		log.debug("Authentication failed : {}", ex.getDetail());
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<ErrorResponse> handleAuthorizationException(AuthorizationException ex) {
		ErrorResponse response = ErrorResponse.of(ErrorCode.AUTHORIZATION_FAILED);
		log.debug("Request does not have authorization : {}", ex.getDetail());
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
		log.debug("Parameter type is invalid");
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		ErrorResponse response = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE);
		log.debug("Argument validation has failed: {}", ex.getMessage());
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
		ErrorResponse response = ErrorResponse.of(ex.getErrorCode());
		log.error("Unexpected error has occurred: {}", ex.getMessage(), ex);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleExceptionHandler(Exception ex) {
		ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(response, response.getStatus());
	}
}
