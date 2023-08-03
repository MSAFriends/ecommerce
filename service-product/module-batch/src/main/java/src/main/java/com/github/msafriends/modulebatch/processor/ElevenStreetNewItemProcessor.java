package src.main.java.com.github.msafriends.modulebatch.processor;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import src.main.java.com.github.msafriends.modulebatch.csv.ElevenStreetCSV;
import src.main.java.com.github.msafriends.modulebatch.csv.ElevenStreetCSVImpl;
import src.main.java.com.github.msafriends.modulebatch.csv.ExtractedElevenStreetCSV;

/**
 * input : ElevenStreetCSV
 * output : ElevenStreetCSV
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ElevenStreetNewItemProcessor implements ItemProcessor<ElevenStreetCSVImpl, ElevenStreetCSV> {
    private final DataSource dataSource;
    private final Random random;
    @Override
    @Transactional
    public ElevenStreetCSV process(ElevenStreetCSVImpl item) throws Exception {
        try{
            String benefit = item.getBenefit();
            ExtractedElevenStreetCSV processedItem = new ExtractedElevenStreetCSV(
                Long.parseLong(item.getProductCode()),
                item.getProductName(),
                item.getProductPrice(),
                item.getSalePrice(),
                random.nextInt(30000),
                item.getDelivery(),
                extractIntValue(benefit, "Discount"),
                extractIntValue(benefit, "Mileage"),
                extractAgeLimit(item.getMinorYn()),
                item.getSellerId(),
                item.getId(),
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
        }catch (Exception e){
            log.error("exception : {}", e.getMessage());
        }
        return item;
    }

    private String extractAgeLimit(String isMinor){
        return isMinor.equals("Y") ? "MINOR" : "ADULT";
    }

    private static int extractIntValue(String jsonString, String fieldName) {
        Pattern pattern = Pattern.compile("'" + fieldName + "': '\\d+'");
        Matcher matcher = pattern.matcher(jsonString);

        if (matcher.find()) {
            String fieldValue = matcher.group();
            String intValue = fieldValue.replaceAll("\\D", ""); // 숫자를 제외한 모든 문자를 제거하여 숫자 값만 추출
            return Integer.parseInt(intValue);
        }

        return 0; // 해당 필드가 존재하지 않을 경우 기본값인 0을 반환
    }
}
