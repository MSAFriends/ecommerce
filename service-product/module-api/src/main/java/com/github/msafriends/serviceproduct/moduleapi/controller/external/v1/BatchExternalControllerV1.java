package com.github.msafriends.serviceproduct.moduleapi.controller.external.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.msafriends.serviceproduct.moduleapi.service.product.batch.BatchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/batch")
public class BatchExternalControllerV1 {
    private final BatchService batchService;

    @PostMapping("/init/product/migration")
    public ResponseEntity<String> runProductJob() throws Exception{
        batchService.startElevenStreetProductMigration();
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/init/product-image/migration")
    public ResponseEntity<String> runProductImageJob() throws Exception{
        batchService.startElevenStreetProductImageMigration();
        return ResponseEntity.ok("ok");
    }
}
