package com.github.msafriends.serviceproduct.modulecore.domain.productimage;


import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

import java.util.Map;

@Getter
@Entity
@Table(name = "product_images")
@NoArgsConstructor(access = PROTECTED)
public class ProductImage {
    private static final String BASE_KEY = "base";

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Column(nullable = false)
    private String base;
    @Column(name = "size_100")
    private String size100;
    @Column(name = "size_110")
    private String size110;
    @Column(name = "size_120")
    private String size120;
    @Column(name = "size_130")
    private String size130;
    @Column(name = "size_140")
    private String size140;
    @Column(name = "size_150")
    private String size150;
    @Column(name = "size_170")
    private String size170;
    @Column(name = "size_200")
    private String size200;
    @Column(name = "size_250")
    private String size250;
    @Column(name = "size_270")
    private String size270;
    @Column(name = "size_300")
    private String size300;

    @Builder
    public ProductImage(Long id, Product product, String base, String size100, String size110, String size120, String size130, String size140, String size150, String size170, String size200, String size250, String size270, String size300) {
        this.id = id;
        this.product = product;
        this.base = base;
        this.size100 = size100;
        this.size110 = size110;
        this.size120 = size120;
        this.size130 = size130;
        this.size140 = size140;
        this.size150 = size150;
        this.size170 = size170;
        this.size200 = size200;
        this.size250 = size250;
        this.size270 = size270;
        this.size300 = size300;
    }

    public static ProductImage createProductImageWithResize(Product product, Map<String, String> fileNameMap){
        return ProductImage.builder()
            .product(product)
            .base(fileNameMap.get(BASE_KEY))
            .size100(fileNameMap.getOrDefault(ResizedPixel.SIZE_100.getPixelKey(), null))
            .size110(fileNameMap.getOrDefault(ResizedPixel.SIZE_110.getPixelKey(), null))
            .size120(fileNameMap.getOrDefault(ResizedPixel.SIZE_120.getPixelKey(), null))
            .size130(fileNameMap.getOrDefault(ResizedPixel.SIZE_130.getPixelKey(), null))
            .size140(fileNameMap.getOrDefault(ResizedPixel.SIZE_140.getPixelKey(), null))
            .size150(fileNameMap.getOrDefault(ResizedPixel.SIZE_150.getPixelKey(), null))
            .size170(fileNameMap.getOrDefault(ResizedPixel.SIZE_170.getPixelKey(), null))
            .size200(fileNameMap.getOrDefault(ResizedPixel.SIZE_200.getPixelKey(), null))
            .size250(fileNameMap.getOrDefault(ResizedPixel.SIZE_250.getPixelKey(), null))
            .size270(fileNameMap.getOrDefault(ResizedPixel.SIZE_270.getPixelKey(), null))
            .size300(fileNameMap.getOrDefault(ResizedPixel.SIZE_300.getPixelKey(), null))
            .build();
    }
}
