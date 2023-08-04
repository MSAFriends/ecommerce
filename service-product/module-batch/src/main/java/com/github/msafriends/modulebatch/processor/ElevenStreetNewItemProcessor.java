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

import com.github.msafriends.modulebatch.csv.ExtractedElevenStreetCSV;

/**
 * input : ElevenStreetCSV
 * output : ElevenStreetCSV
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ElevenStreetNewItemProcessor implements ItemProcessor<ElevenStreetCSV, ExtractedElevenStreetCSV> {
    private static Long PRODUCT_ID_LIMIT = 13406L;

    private final DataSource dataSource;

    @Override
    @Transactional
    public ExtractedElevenStreetCSV process(ElevenStreetCSV item) throws Exception {
        String benefit = item.getBenefit();
        Random random = new Random();
        ExtractedElevenStreetCSV processedItem = new ExtractedElevenStreetCSV(
            Long.parseLong(item.getProductCode()),
            item.getProductName(),
            item.getProductPrice(),
            item.getSalePrice(),
            random.nextInt(30000),
            item.getDelivery(),
            item.getBuySatisfy(),
            extractIntValue(benefit, "Discount"),
            extractIntValue(benefit, "Mileage"),
            extractAgeLimit(item.getMinorYn()),
            item.getSellerId(),
            diversion(item.getId(), random),
            item.getProductImage(),
            item.getProductImage100(),
            item.getProductImage110(),
            item.getProductImage120(),
            item.getProductImage130(),
            item.getProductImage140(),
            item.getProductImage150(),
            item.getProductImage170(),
            item.getProductImage200(),
            item.getProductImage250(),
            item.getProductImage270(),
            item.getProductImage300()
        );
        log.info("step = {} {}", item.getId(), item.getProductName());
        return processedItem;
    }

    private Long diversion(Long id, Random random){
        if(id > PRODUCT_ID_LIMIT) return random.nextLong(PRODUCT_ID_LIMIT);
        return id;
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
                String intValue = fieldValue.replaceAll("\\D", ""); // 숫자를 제외한 모든 문자를 제거하여 숫자 값만 추출
                return Integer.parseInt(intValue);
            }
        }catch (Exception ex){
            log.error("transforming Benefit value has failed : {}, {}", jsonString, ex.getMessage());
        }
        return 0; // 해당 필드가 존재하지 않을 경우 기본값인 0을 반환
    }
}
