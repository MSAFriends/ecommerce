package com.github.msafriends.modulecommon.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "S_001", "서버에 오류가 발생하였습니다."),
	AUTHORIZATION_FAILED(HttpStatus.UNAUTHORIZED, "AR_001", "권한이 없습니다."),
	AUTHENTICATION_FAILED(HttpStatus.FORBIDDEN, "AU_001", "인증에 실패하였습니다."),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C_001", "유효하지 않은 입력입니다."),
	NOT_EMPTY(HttpStatus.BAD_REQUEST, "C_002", "값이 필요합니다."),

	INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "M_002", "유효하지 않은 이메일 형식입니다."),
	INVALID_PHONE_NUMBER_FORMAT(HttpStatus.BAD_REQUEST, "M_002", "유효하지 않은 전화번호 형식입니다."),
	EXPIRED_COUPON_ERROR(HttpStatus.BAD_REQUEST, "CO_001", "이미 만료된 쿠폰입니다."),
	INVALID_COUPON_DATE(HttpStatus.INTERNAL_SERVER_ERROR, "CO_002", "쿠폰의 유효기간이 잘못되었습니다."),
	INVALID_COUPON_STRATEGY(HttpStatus.INTERNAL_SERVER_ERROR, "CO_003", "유효하지 않은 비율정책입니다."),

	INVALID_MAINTENANCE_TIME(HttpStatus.INTERNAL_SERVER_ERROR, "B_001", "은행 점검기간이 유효하게 설정되지 않았습니다."),
	INVALID_CARD_NUMBER(HttpStatus.BAD_REQUEST, "CA_001", "카드번호가 잘못되었습니다."),
	INVALID_DATE_FORMAT(HttpStatus.BAD_REQUEST, "CA_002", "유효하지 않은 카드 유효기간 날짜 형식입니다."),
	INVALID_PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "CA_003", "잘못된 비밀번호 입니다."),
	INVALID_PRICE_ERROR(HttpStatus.BAD_REQUEST, "P_001", "유효하지 않은 가격입니다."),
	INVALID_QUANTITY_ERROR(HttpStatus.BAD_REQUEST, "P_002", "유효하지 않은 수량입니다.");

	private final HttpStatus status;
	private final String code;
	private final String message;

	ErrorCode(final HttpStatus status, final String code, final String message) {
		this.status = status;
		this.code = code;
		this.message = message;
	}
}
