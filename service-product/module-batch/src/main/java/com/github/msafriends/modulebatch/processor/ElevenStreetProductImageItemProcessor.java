package com.github.msafriends.modulebatch.processor;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.msafriends.modulebatch.csv.ElevenStreetCSV;
import com.github.msafriends.modulebatch.csv.ProductElevenStreetCSV;
import com.github.msafriends.modulebatch.csv.ProductImageElevenStreetCSV;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * input : ElevenStreetCSV
 * output : ElevenStreetCSV
 */
@Slf4j
@Component

public class ElevenStreetProductImageItemProcessor implements ItemProcessor<ElevenStreetCSV, ProductImageElevenStreetCSV> {
    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    public ElevenStreetProductImageItemProcessor(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    @Transactional
    public ProductImageElevenStreetCSV process(ElevenStreetCSV item) throws Exception {
        ProductImageElevenStreetCSV processedItem = new ProductImageElevenStreetCSV(
            findProductIdByCode(Long.parseLong(item.getProductCode())),
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

    private Long findProductIdByCode(Long code){
        List<Long> productIds = jdbcTemplate
            .query(
                "SELECT product_id FROM products WHERE code = ?",
                productIdRowMapper(), code
            );
        return productIds.stream().findFirst().orElse(null);
    }

    private RowMapper<Long> productIdRowMapper(){
        return (rs, rowNum) -> rs.getLong("product_id");
    }
}
