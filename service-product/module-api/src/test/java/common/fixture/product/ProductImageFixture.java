package common.fixture.product;

import domain.product.Product;
import domain.productimage.ProductImage;

public class ProductImageFixture {


    private static final String BASE = "https://cdn.011st.com/11dims/resize/x80/11src/product/5994781350/L300.jpg?435000000";
    private static final String SIZE_100 = "https://cdn.011st.com/11dims/resize/x100/11src/product/5994781350/L300.jpg?435000000";
    private static final String SIZE_110 = "https://cdn.011st.com/11dims/resize/x110/11src/product/5994781350/L300.jpg?435000000";
    private static final String SIZE_120 = "https://cdn.011st.com/11dims/resize/x120/11src/product/5994781350/L300.jpg?435000000";
    private static final String SIZE_130 = "https://cdn.011st.com/11dims/resize/x130/11src/product/5994781350/L300.jpg?435000000";
    private static final String SIZE_140 = "https://cdn.011st.com/11dims/resize/x140/11src/product/5994781350/L300.jpg?435000000";
    private static final String SIZE_150 = "https://cdn.011st.com/11dims/resize/x150/11src/product/5994781350/L300.jpg?435000000";
    private static final String SIZE_170 = "https://cdn.011st.com/11dims/resize/x170/11src/product/5994781350/L300.jpg?435000000";
    private static final String SIZE_200 = "https://cdn.011st.com/11dims/resize/x200/11src/product/5994781350/L300.jpg?435000000";
    private static final String SIZE_250 = "https://cdn.011st.com/11dims/resize/x250/11src/product/5994781350/L300.jpg?435000000";
    private static final String SIZE_270 = "https://cdn.011st.com/11dims/resize/x270/11src/product/5994781350/L300.jpg?435000000";
    private static final String SIZE_300 = "https://cdn.011st.com/11dims/resize/x300/11src/product/5994781350/L300.jpg?435000000";

    public static ProductImage createProductImage(Product product) {
        return ProductImage.builder()
                .product(product)
                .base(BASE)
                .size100(SIZE_100)
                .size110(SIZE_110)
                .size120(SIZE_120)
                .size130(SIZE_130)
                .size140(SIZE_140)
                .size150(SIZE_150)
                .size170(SIZE_170)
                .size200(SIZE_200)
                .size250(SIZE_250)
                .size270(SIZE_270)
                .size300(SIZE_300)
                .build();
    }
}
