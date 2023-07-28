package domain.review;


import domain.product.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.util.Assert;

import static lombok.AccessLevel.*;

import com.github.msafriends.modulecommon.base.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@DiscriminatorValue("PRODUCT")
public class ProductReview extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;
    @Min(value = 0, message = "0점 이상의 점수를 입력해주세요")
    @Max(value = 5, message = "5점 이하의 점수를 입력해주세요")
    private int rating;
    @Column(nullable = false, length = 50)
    private String title;
    @Column(nullable = false, length = 500)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
    @NotNull
    private Long memberId;

    @Builder
    public ProductReview(int rating, String title, String content, Product product, Long memberId) {
        validateProductReview(rating, product, memberId, title, content);
        this.rating = rating;
        this.title = title;
        this.content = content;
        this.product = product;
        this.memberId = memberId;
    }

    private void validateProductReview(int rating, Product product, Long memberId, String title, String content){
        validateNotNull(product, memberId, title, content);
        validateRating(rating);
    }
    private void validateRating(int rating) {
        Assert.isTrue(rating >= 0 && rating <= 5, "평점은 0 ~ 5점 사이로 입력해주세요.");
    }

    private void validateNotNull(Product product, Long memberId, String title, String content){
        Assert.notNull(product, "Product must not be null");
        Assert.notNull(memberId, "memberId must not be null");
        Assert.notNull(title, "title must not be null");
        Assert.notNull(content, "content must not be null");
    }
}
