package com.github.msafriends.serviceproduct.modulecore.domain.review;

import com.github.msafriends.modulecommon.base.BaseTimeEntity;
import com.github.msafriends.modulecommon.exception.ErrorCode;
import com.github.msafriends.modulecommon.exception.InvalidValueException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseTimeEntity {
	private static final int MAX_CONTENT_LENGTH = 500;

	@OneToOne
	@JoinColumn(name = "review_id", nullable = false)
	private ProductReview review;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reply_id")
	private Long id;
	@Column(nullable = false, length = MAX_CONTENT_LENGTH)
	private String content;
	@Column(nullable = false)
	private Long sellerId;

	@Builder
	public Reply(String content, Long sellerId, ProductReview review) {
		validateReply(content, sellerId, review);
		this.content = content;
		this.sellerId = sellerId;
		this.review = review;
	}

	public void validateReply(String content, Long sellerId, ProductReview review){
		validateNotNull(content, sellerId, review);
		validateContentLength(content);
	}

	private void validateContentLength(String content){
		if(content.length() > MAX_CONTENT_LENGTH) throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE, "content는 500자 이상 작성하실수 없습니다.");
	}

	private void validateNotNull(String content, Long sellerId, ProductReview review){
		if(isNull(content)) throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE, "content는 null이어선 안됩니다.");
		if(isNull(sellerId)) throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE, "sellerId는 null이어선 안됩니다.");
		if(isNull(review)) throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE, "review는 null이어선 안됩니다.");
	}

	public boolean isNull(Object param){
		return param == null;
	}
}
