package com.github.msafriends.modulecommon.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.msafriends.modulecommon.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
	private String message;
	private String code;
	@JsonIgnore
	private HttpStatus status;

	@Builder
	private ErrorResponse(HttpStatus status,String code, String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}

	public static ErrorResponse of(ErrorCode errorCode){
		return ErrorResponse.builder()
				.status(errorCode.getStatus())
				.code(errorCode.getCode())
				.message(errorCode.getMessage())
				.build();
	}

	public static ErrorResponse of(ErrorCode errorCode, String message) {
		return ErrorResponse.builder()
				.status(errorCode.getStatus())
				.code(errorCode.getCode())
				.message(message)
				.build();
	}
}
