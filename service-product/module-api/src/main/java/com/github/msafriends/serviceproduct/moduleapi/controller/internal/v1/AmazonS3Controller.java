package com.github.msafriends.serviceproduct.moduleapi.controller.internal.v1;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.msafriends.serviceproduct.moduleapi.service.productimage.AwsS3Service;
import com.github.msafriends.serviceproduct.modulecore.dto.productimage.ProductImageResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/v1/s3")
public class AmazonS3Controller {
    private final AwsS3Service awsS3Service;

    @PostMapping("/products/{productId}/images")
    public ResponseEntity<ProductImageResponse> uploadFiles(
        @RequestPart MultipartFile multipartFile,
        @PathVariable Long productId
    ){
        return ResponseEntity.ok(awsS3Service.uploadFile(multipartFile, productId));
    }

    @DeleteMapping("/products/{imageId}/images")
    public ResponseEntity<Void> deleteFile(
        @RequestParam String fileName,
        @PathVariable(value = "imageId") Long productImageId
    ){
        awsS3Service.deleteFile(fileName, productImageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/products/images")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam String fileKey){
        ByteArrayResource downloadedFile = awsS3Service.download(fileKey);
        return ResponseEntity
            .ok()
            .contentLength(downloadedFile.contentLength())
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header("Content-disposition", "attachment; filename=\"" + fileKey +"\"")
            .body(downloadedFile);
    }
}
