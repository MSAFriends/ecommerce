package com.github.msafriends.moduleapi.controller.external.member;

import com.github.msafriends.moduleapi.service.BatchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/batch")
public class BatchExternalControllerV1 {
    private final BatchService batchService;

    @PostMapping("/init/seller")
    public ResponseEntity<String> runJob() throws Exception {
        batchService.startMyJob();
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/export/seller")
    public ResponseEntity<String> runCsvJob() throws Exception {
        batchService.startCsvJob();
        return ResponseEntity.ok("ok");
    }
}
