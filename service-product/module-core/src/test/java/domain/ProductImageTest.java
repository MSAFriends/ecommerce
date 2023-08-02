package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.msafriends.serviceproduct.modulecore.domain.product.Product;
import com.github.msafriends.serviceproduct.modulecore.domain.productimage.ProductImage;
import fixture.product.ProductFixture;

public class ProductImageTest {



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

    @Test
    @DisplayName("상품 이미지 등록이 성공적으로 이루어진다.")
    void successRegisterProductImage() {

        Product product = ProductFixture.createProduct();
        ProductImage productImage = ProductImage.builder()
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
                .product(product)
                .build();
        Assertions.assertThat(productImage.getBase()).isEqualTo(BASE);
        Assertions.assertThat(productImage.getSize100()).isEqualTo(SIZE_100);
        Assertions.assertThat(productImage.getSize110()).isEqualTo(SIZE_110);
        Assertions.assertThat(productImage.getSize120()).isEqualTo(SIZE_120);
        Assertions.assertThat(productImage.getSize130()).isEqualTo(SIZE_130);
        Assertions.assertThat(productImage.getSize140()).isEqualTo(SIZE_140);
        Assertions.assertThat(productImage.getSize150()).isEqualTo(SIZE_150);
        Assertions.assertThat(productImage.getSize170()).isEqualTo(SIZE_170);
        Assertions.assertThat(productImage.getSize200()).isEqualTo(SIZE_200);
        Assertions.assertThat(productImage.getSize250()).isEqualTo(SIZE_250);
        Assertions.assertThat(productImage.getSize270()).isEqualTo(SIZE_270);
        Assertions.assertThat(productImage.getSize300()).isEqualTo(SIZE_300);
        Assertions.assertThat(productImage.getProduct()).isEqualTo(product);
    }
}
