package com.github.msafriends.modulecommon.dto;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.msafriends.modulecommon.exception.ErrorCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
	private String message;
	private String code;
	private List<FieldError> fieldErrors;
	@JsonIgnore
	private HttpStatus status;

	@Builder
	private ErrorResponse(HttpStatus status,String code, String message, List<FieldError> fieldErrors) {
		this.status = status;
		this.code = code;
		this.message = message;
		this.fieldErrors = fieldErrors;
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

	public static ErrorResponse of(ErrorCode errorCode, Map<String, String> fieldErrors) {
		List<FieldError> errors = fieldErrors.entrySet().stream()
				.map(entry -> new FieldError(entry.getKey(), entry.getValue())).toList();

		return ErrorResponse.builder()
				.status(errorCode.getStatus())
				.code(errorCode.getCode())
				.fieldErrors(errors)
				.build();
	}

	@Getter
	@NoArgsConstructor(access = AccessLevel.PROTECTED)
	protected static class FieldError {
		private String field;
		private String message;

		public FieldError(String field, String message) {
			this.field = field;
			this.message = message;
		}
	}
}
