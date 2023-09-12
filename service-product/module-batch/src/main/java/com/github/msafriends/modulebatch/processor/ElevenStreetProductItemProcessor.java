package com.github.msafriends.modulebatch.processor;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.msafriends.modulebatch.csv.ElevenStreetCSV;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.github.msafriends.modulebatch.csv.ProductElevenStreetCSV;

/**
 * input : ElevenStreetCSV
 * output : ElevenStreetCSV
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ElevenStreetProductItemProcessor implements ItemProcessor<ElevenStreetCSV, ProductElevenStreetCSV> {
    private static final int REPLACED_QUANTITY = 2000;

    private final DataSource dataSource;

    @Override
    @Transactional
    public ProductElevenStreetCSV process(ElevenStreetCSV item) throws Exception {
        String benefit = item.getBenefit();
        ProductElevenStreetCSV processedItem = new ProductElevenStreetCSV(
            Long.parseLong(item.getProductCode()),
            item.getProductName(),
            item.getProductPrice(),
            item.getSalePrice(),
            REPLACED_QUANTITY,
            item.getDelivery(),
            (float)item.getBuySatisfy(),
            extractIntValue(benefit, "Discount"),
            extractIntValue(benefit, "Mileage"),
            extractAgeLimit(item.getMinorYn()),
            item.getSellerId()
        );
        log.info("step = {} {}", item.getId(), item.getProductName());
        return processedItem;
    }

    private String extractAgeLimit(String isMinor){
        return isMinor.equals("Y") ? "MINOR" : "ADULT";
    }

    private static int extractIntValue(String jsonString, String fieldName) {
        try {
            Pattern pattern = Pattern.compile("'" + fieldName + "': '\\d+'");
            Matcher matcher = pattern.matcher(jsonString);
            if (matcher.find()) {
                String fieldValue = matcher.group();
                String intValue = fieldValue.replaceAll("\\D", "");
                return Integer.parseInt(intValue);
            }
        }catch (Exception ex){
            log.error("transforming Benefit value has failed : {}, {}", jsonString, ex.getMessage());
        }
        return 0;
    }
}
