package com.github.msafriends.modulebatch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.github.msafriends.modulebatch.csv.ElevenStreetCSVImpl;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElevenStreetItemProcessor implements ItemProcessor<ElevenStreetCSVImpl, ElevenStreetCSVImpl> {
    @Override
    @Transactional
    public ElevenStreetCSVImpl process(ElevenStreetCSVImpl item) throws Exception {
        try{
            log.info("step = {} {}", item.getId(), item.getProductName());
        }catch (Exception e){
            log.error("exception : {}", e.getMessage());
        }
        return item;
    }
}
