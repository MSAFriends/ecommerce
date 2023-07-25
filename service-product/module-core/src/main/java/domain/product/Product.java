package domain.product;

import domain.review.Review;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Product {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private Long sellerId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    @Column(unique = true)
    @NotNull
    private Long code;
    @NotNull
    private String name;
    private int price;
    private int salePrice;
    private float rating;
    @NotNull
    private String detailPageUrl;
    @NotNull
    private String delivery;
    private int reviewCount;
    private int buySatisfy;
    @NotNull
    private String isMinor;
    private int quantity;
    @Embedded
    private Benefit benefit;

    @Deprecated(since = "BaseTimeEntity를 상속받아서 사용할 예정")
    @CreatedDate
    private LocalDateTime createdAt;

    @Deprecated(since = "BaseTimeEntity를 상속받아서 사용할 예정")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Product(Long id, Long sellerId, List<ProductImage> productImages, Long code, String name, int price, int salePrice, float rating, String detailPageUrl, String delivery, int reviewCount, int buySatisfy, String isMinor, int quantity, Benefit benefit) {
        validateProduct(id, code, name, detailPageUrl, delivery, isMinor, quantity, price, salePrice);
        this.id = id;
        this.sellerId = sellerId;
        this.productImages = productImages;
        this.code = code;
        this.name = name;
        this.price = price;
        this.salePrice = salePrice;
        this.rating = rating;
        this.detailPageUrl = detailPageUrl;
        this.delivery = delivery;
        this.reviewCount = reviewCount;
        this.buySatisfy = buySatisfy;
        this.isMinor = isMinor;
        this.quantity = quantity;
        this.benefit = benefit;
    }

    private void validateProduct(Long id, Long code, String name, String detailPageUrl, String delivery, String isMinor, int quantity, int price, int salePrice) {
        validateSalePrice(price, salePrice);
        validateNotNull(id, code, name, detailPageUrl, delivery, isMinor);
        validateQuantity(quantity);
    }

     private void validateQuantity(int quantity) {
         if (quantity < 0) {
             throw new IllegalArgumentException("수량은 0보다 커야 합니다.");
         }
     }

     private void validateSalePrice(int price, int salePrice) {
        if (price < 0) {
            throw new IllegalArgumentException("가격은 0보다 커야 합니다.");
        }

        if (salePrice < 0) {
            throw new IllegalArgumentException("할인 가격은 0보다 커야 합니다.");
        }

        if (salePrice > price) {
            throw new IllegalArgumentException("할인 가격은 가격보다 작아야 합니다.");
        }
     }

     private void validateNotNull(Long memberId, Long code, String name, String detailPageUrl, String delivery, String isMinor) {
        Assert.notNull(memberId, "memberId must not be null");
        Assert.notNull(code, "code must not be null");
        Assert.notNull(name, "name must not be null");
        Assert.notNull(detailPageUrl, "detailPageUrl must not be null");
        Assert.notNull(delivery, "delivery must not be null");
        Assert.notNull(isMinor, "isMinor must not be null");
    }
}
