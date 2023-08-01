package com.github.msafriends.modulebatch.processor;

import com.github.msafriends.modulebatch.csv.ElevenStreetCSV;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElevenStreetNewItemProcessor implements ItemProcessor<ElevenStreetCSV, ElevenStreetCSV> {
    private final DataSource dataSource;

    @Override
    @Transactional
    public ElevenStreetCSV process(final ElevenStreetCSV item) {
        try {
            String sellerNick = item.getSellerNick();
            Long sellerId = findSellerIdByNickName(sellerNick);

            ElevenStreetCSV newItem = new ElevenStreetCSV(
                    item.getId(),
                    item.getProductCode(),
                    item.getProductName(),
                    item.getProductPrice(),
                    item.getSalePrice(),
                    item.getRating(),
                    item.getDetailPageUrl(),
                    item.getDelivery(),
                    item.getReviewCount(),
                    item.getBuySatisfy(),
                    item.getMinorYn(),
                    item.getBenefit(),
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
                    item.getProductImage300(),
                    item.getText1(),
                    item.getText2(),
                    item.getSellerNick(),
                    item.getSeller(),
                    item.getSellerGrd(),
                    sellerId
            );
            log.info("step = {} {}", item.getId(), item.getProductName());
            return newItem;
        } catch (Exception e) {
            log.error("exception = {}", e.getMessage());
        }
        return item;
    }

    private Long findSellerIdByNickName(String nickName) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String sql = "SELECT seller_id FROM SELLERS WHERE nick_name = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, nickName);
    }
}