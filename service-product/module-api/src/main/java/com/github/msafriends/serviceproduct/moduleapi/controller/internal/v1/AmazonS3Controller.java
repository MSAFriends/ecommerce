package com.github.msafriends.serviceproduct.moduleapi.controller.internal.v1;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;
import com.github.msafriends.serviceproduct.modulecore.exception.FileProcessingException;

import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/products/images")
    public ResponseEntity<ByteArrayResource> downloadFile(
        @RequestParam String fileKey,
        @RequestParam(required = false) String downloadFileName,
        HttpServletRequest request
    ){
        ByteArrayResource downloadedFile = awsS3Service.download(fileKey);
        return ResponseEntity
            .ok()
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .header("Content-disposition", "attachment; filename=\"" + getEncodedFileName(request.getHeader("User-Agent"), fileKey) +"\"")
            .body(downloadedFile);
    }

    private static String chromeEncoding(String displayFileName) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < displayFileName.length(); i++) {
            char c = displayFileName.charAt(i);
            if(c > '~'){
                sb.append(URLEncoder.encode("" + c, "UTF-8"));
            }else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    private String getEncodedFileName(String agentName, String displayFileName) {
        String encodedFileName = null;
        try {
            if(agentName.contains("MSIE")){
                encodedFileName = URLEncoder.encode(displayFileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            } else if (agentName.contains("Trident")) {
                encodedFileName = URLEncoder.encode(displayFileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
            } else if (agentName.contains("Chrome")){
                encodedFileName = chromeEncoding(displayFileName);
            } else if (agentName.contains("Opera")) {
                encodedFileName = "\"" + new String(displayFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + "\"";
            } else {
                encodedFileName = URLDecoder.decode("\"" + new String(displayFileName.getBytes(StandardCharsets.UTF_8),
                        StandardCharsets.ISO_8859_1) + "\"",
                    StandardCharsets.UTF_8);
            }
        } catch (UnsupportedEncodingException ex){
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR, "인코딩과정에서 문제가 발생했습니다.");
        }
        return encodedFileName;
    }
}
