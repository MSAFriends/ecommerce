package com.github.msafriends.modulebatch.processor;

import com.github.msafriends.modulebatch.csv.ElevenStreetCSV;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElevenStreetItemProcessor implements ItemProcessor<ElevenStreetCSV, ElevenStreetCSV> {

    @Override
    @Transactional
    public ElevenStreetCSV process(final ElevenStreetCSV item) {
        try {
            log.info("step = {} {}", item.getId(), item.getProductName());
        } catch (Exception e) {
            log.error("exception = {}", e.getMessage());
        }
        return item;
    }
}
