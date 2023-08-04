package com.github.msafriends.modulebatch.csv;

import java.lang.reflect.Field;
import java.util.Arrays;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ElevenStreetCSV {
    private Long id;
    private String ProductCode;
    private String ProductName;
    private int ProductPrice;
    private int SalePrice;
    private String Rating;
    private String DetailPageUrl;
    private String Delivery;
    private int ReviewCount;
    private int BuySatisfy;
    private String MinorYn;
    private String Benefit;
    private String ProductImage;
    private String ProductImage100;
    private String ProductImage110;
    private String ProductImage120;
    private String ProductImage130;
    private String ProductImage140;
    private String ProductImage150;
    private String ProductImage170;
    private String ProductImage200;
    private String ProductImage250;
    private String ProductImage270;
    private String ProductImage300;
    private String Text1;
    private String Text2;
    private String SellerNick;
    private String Seller;
    private int SellerGrd;
    private Long sellerId;

    public ElevenStreetCSV(Long id, String productCode, String productName, int productPrice, int salePrice, String rating, String detailPageUrl, String delivery, int reviewCount, int buySatisfy, String minorYn, String benefit, String productImage, String productImage100, String productImage110, String productImage120, String productImage130, String productImage140, String productImage150, String productImage170, String productImage200, String productImage250, String productImage270, String productImage300, String text1, String text2, String sellerNick, String seller, int sellerGrd, Long sellerId) {
        this.id = id;
        ProductCode = productCode;
        ProductName = productName;
        ProductPrice = productPrice;
        SalePrice = salePrice;
        Rating = rating;
        DetailPageUrl = detailPageUrl;
        Delivery = delivery;
        ReviewCount = reviewCount;
        BuySatisfy = buySatisfy;
        MinorYn = minorYn;
        Benefit = benefit;
        ProductImage = productImage;
        ProductImage100 = productImage100;
        ProductImage110 = productImage110;
        ProductImage120 = productImage120;
        ProductImage130 = productImage130;
        ProductImage140 = productImage140;
        ProductImage150 = productImage150;
        ProductImage170 = productImage170;
        ProductImage200 = productImage200;
        ProductImage250 = productImage250;
        ProductImage270 = productImage270;
        ProductImage300 = productImage300;
        Text1 = text1;
        Text2 = text2;
        SellerNick = sellerNick;
        Seller = seller;
        SellerGrd = sellerGrd;
        this.sellerId = sellerId;
    }

    public static String[] getFieldNames() {
        Field[] fields = ElevenStreetCSV.class.getDeclaredFields();
        return Arrays.stream(fields)
            .map(Field::getName)
            .toArray(String[]::new);
    }
}