package com.github.msafriends.serviceproduct.moduleapi.controller.external.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.msafriends.serviceproduct.moduleapi.service.BatchService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/batch")
public class BatchExternalControllerV1 {
    private final BatchService batchService;

    @PostMapping("/init/migration")
    public ResponseEntity<String> runJob() throws Exception{
        batchService.startElevenStreetProductAndProductImageDataMigration();
        return ResponseEntity.ok("ok");
    }
}
