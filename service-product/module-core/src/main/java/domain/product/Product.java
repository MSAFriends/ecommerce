package domain.product;

import domain.review.Review;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Product {

    @Id
    @GeneratedValue
    @Column(name = "product_id")
    private Long id;
    private Long sellerId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductImage> productImages = new ArrayList<>();

    @Column(unique = true)
    private Long code;
    private String name;
    private int price;
    private int salePrice;
    @Column(name = "상품 평점")
    private float rating;
    @Column(name = "상품 소개 페이지 url")
    private String detailPageUrl;
    private String delivery;
    private int reviewCount;
    private int buySatisfy;
    private String isMinor;

    @Embedded
    private Benefit benefit;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public Product(Long id, Long sellerId, List<Review> reviews, List<ProductImage> productImages, Long code, String name, int price, int salePrice, float rating, String detailPageUrl, String delivery, int reviewCount, int buySatisfy, String isMinor, Benefit benefit) {
        this.id = id;
        this.sellerId = sellerId;
        this.reviews = reviews;
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
        this.benefit = benefit;
    }
}
