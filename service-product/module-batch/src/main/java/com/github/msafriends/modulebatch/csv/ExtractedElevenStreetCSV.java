package com.github.msafriends.modulebatch.csv;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExtractedElevenStreetCSV {
    private Long code;
    private String name;
    private int priceValue;
    private int salePriceValue;
    private int quantity;
    private String delivery;
    private int buySatisfy;
    private int discount;
    private int mileage;
    private String ageLimit;
    private Long sellerId;
    private Long productId;
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

    public ExtractedElevenStreetCSV(Long code, String name, int priceValue, int salePriceValue, int quantity,
        String delivery, int buySatisfy, int discount, int mileage, String ageLimit, Long sellerId, Long productId,
        String base, String size100, String size110, String size120, String size130, String size140, String size150,
        String size170, String size200, String size250, String size270, String size300) {
        this.code = code;
        this.name = name;
        this.priceValue = priceValue;
        this.salePriceValue = salePriceValue;
        this.quantity = quantity;
        this.delivery = delivery;
        this.buySatisfy = buySatisfy;
        this.discount = discount;
        this.mileage = mileage;
        this.ageLimit = ageLimit;
        this.sellerId = sellerId;
        this.productId = productId;
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
}
