package com.github.msafriends.serviceproduct.moduleapi.controller.internal.v1;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.msafriends.serviceproduct.moduleapi.service.productimage.AwsS3Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/v1/s3")
public class AmazonS3Controller {
    private final AwsS3Service awsS3Service;

    @PostMapping("/products/{productId}/images")
    public ResponseEntity<List<String>> uploadFiles(
        @RequestPart List<MultipartFile> multipartFiles,
        @PathVariable Long productId
    ){
        return ResponseEntity.ok(awsS3Service.uploadFile(multipartFiles, productId));
    }

    @DeleteMapping("/products/{imageId}/images")
    public ResponseEntity<Void> deleteFile(
        @RequestParam String fileName,
        @PathVariable(value = "imageId") Long productImageId
    ){
        awsS3Service.deleteFile(fileName, productImageId);
        return ResponseEntity.noContent().build();
    }
}
