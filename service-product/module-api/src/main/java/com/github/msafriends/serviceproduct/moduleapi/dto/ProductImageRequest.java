package com.github.msafriends.serviceproduct.moduleapi.dto;


import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.domain.productimage.ProductImage;
import lombok.Getter;

@Getter
public class ProductImageRequest {

    private String base;
    private String size100;
    private String size110;
    private String size120;
    private String size130;
    private String size140;
    private String size150;
    private String size170;
    private String size200;
    private String size250;
    private String size270;
    private String size300;

    public static ProductImage toEntity(ProductImageRequest request, Product product) {
        return ProductImage.builder()
                .base(request.getBase())
                .product(product)
                .size100(request.getSize100())
                .size110(request.getSize110())
                .size120(request.getSize120())
                .size130(request.getSize130())
                .size140(request.getSize140())
                .size150(request.getSize150())
                .size170(request.getSize170())
                .size200(request.getSize200())
                .size250(request.getSize250())
                .size270(request.getSize270())
                .size300(request.getSize300())
                .build();
    }
}
