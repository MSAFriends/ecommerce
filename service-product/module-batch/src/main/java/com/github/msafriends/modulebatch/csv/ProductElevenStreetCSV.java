package com.github.msafriends.modulebatch.csv;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductElevenStreetCSV {
    private Long code;
    private String name;
    private int priceValue;
    private int salePriceValue;
    private int quantity;
    private String delivery;
    private float buySatisfy;
    private int discount;
    private int mileage;
    private String ageLimit;
    private Long sellerId;

    public ProductElevenStreetCSV(Long code, String name, int priceValue, int salePriceValue, int quantity,
        String delivery,
        float buySatisfy, int discount, int mileage, String ageLimit, Long sellerId) {
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
    }
}
