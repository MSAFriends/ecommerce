package domain.review;


import domain.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

import static lombok.AccessLevel.*;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull
    private Long memberId;

    @Min(value = 0, message = "0점 이상의 점수를 입력해주세요")
    @Max(value = 5, message = "5점 이하의 점수를 입력해주세요")
    private int rating;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false, length = 500)
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Review(Long memberId, Product product, int rating, String title, String content) {
        validateReview(memberId, title, content, rating);
        this.memberId = memberId;
        this.product = product;
        this.rating = rating;
        this.title = title;
        this.content = content;
    }

    private void validateReview(Long memberId, String title, String content, int rating) {
        validateNotNull(memberId, title, content);
        validateRating(rating);
    }

    private void validateRating(int rating) {
        Assert.isTrue(rating >= 0 && rating <= 5, "평점은 0 ~ 5점 사이로 입력해주세요.");
    }

    private void validateNotNull(Long memberId, String title, String content) {
        Assert.notNull(memberId, "title must not be null");
        Assert.notNull(title, "title must not be null");
        Assert.notNull(content, "content must not be null");
    }
}
