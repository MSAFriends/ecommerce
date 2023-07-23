package fixture.product;

import domain.product.Benefit;
import domain.product.Product;

public class ProductFixture {

    private static final Long PRODUCT_ID = 1L;
    private static final Long SELLER_ID = 1L;
    private static final Long CODE = 111111111L;
    private static final int PRICE = 1000;
    private static final int SALE_PRICE = 900;
    private static final String NAME = "상품명";
    private static final float RATING = 2.5F;
    private static final String DETAIL_PAGE_URL = "localhost:10000/asset/image.jpg";
    private static final String DELIVERY = "착불/선결제";
    private static final int REVIEW_COUNT = 10;
    private static final int BUY_SATISFY = 90;
    private static final String IS_MINOR = "N";

    public static Product createProduct(Benefit benefit) {
        return Product.builder()
                .id(PRODUCT_ID)
                .sellerId(SELLER_ID)
                .code(CODE)
                .price(PRICE)
                .salePrice(SALE_PRICE)
                .name(NAME)
                .rating(RATING)
                .detailPageUrl(DETAIL_PAGE_URL)
                .delivery(DELIVERY)
                .reviewCount(REVIEW_COUNT)
                .buySatisfy(BUY_SATISFY)
                .isMinor(IS_MINOR)
                .benefit(benefit)
                .build();
    }
}
