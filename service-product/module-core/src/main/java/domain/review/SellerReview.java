package domain.review;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class SellerReview {

    @Id @GeneratedValue
    private Long id;

    @Min(value = 0, message = "0점 이상의 점수를 입력해주세요")
    @Max(value = 5, message = "5점 이하의 점수를 입력해주세요")
    private int rating;
    private Long sellerId;

    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 500)
    private String content;


    @Builder
    public SellerReview(int rating, Long sellerId, String title, String content) {
        validateSellerReview(title, content, rating);
        this.rating = rating;
        this.sellerId = sellerId;
        this.title = title;
        this.content = content;
    }

    private void validateSellerReview(String title, String content, int rating) {
        validateNotNull(title, content);
        validateRating(rating);
    }

    private void validateRating(int rating) {
        Assert.isTrue(rating >= 0 && rating <= 5, "평점은 0 ~ 5점 사이로 입력해주세요.");
    }

    private void validateNotNull(String title, String content) {
        Assert.notNull(title, "product must not be null");
        Assert.notNull(content, "content must not be null");
    }
}
