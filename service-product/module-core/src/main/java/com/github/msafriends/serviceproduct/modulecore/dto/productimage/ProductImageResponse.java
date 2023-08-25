package com.github.msafriends.serviceproduct.modulecore.dto.productimage;

import com.github.msafriends.serviceproduct.modulecore.domain.productimage.ProductImage;


public record ProductImageResponse(
        Long id,
        String base,
        String size100,
        String size110,
        String size120,
        String size130,
        String size140,
        String size150,
        String size170,
        String size200,
        String size250,
        String size270,
        String size300
) {

    public static ProductImageResponse from(ProductImage productImage) {
        return new ProductImageResponse(
                productImage.getId(),
                productImage.getBase(),
                productImage.getSize100(),
                productImage.getSize110(),
                productImage.getSize120(),
                productImage.getSize130(),
                productImage.getSize140(),
                productImage.getSize150(),
                productImage.getSize170(),
                productImage.getSize200(),
                productImage.getSize250(),
                productImage.getSize270(),
                productImage.getSize300()
        );
    }
}
