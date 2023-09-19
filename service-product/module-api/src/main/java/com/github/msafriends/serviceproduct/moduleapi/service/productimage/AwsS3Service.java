package com.github.msafriends.serviceproduct.moduleapi.service.productimage;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.github.msafriends.serviceproduct.modulecore.domain.productimage.ProductImage;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;
import com.github.msafriends.serviceproduct.modulecore.exception.FileProcessingException;
import com.github.msafriends.serviceproduct.modulecore.exception.InvalidValueException;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductImageRepository;
import com.github.msafriends.serviceproduct.modulecore.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwsS3Service {

    @Value("${images.path}")
    private String s3Path;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    public List<String> uploadFile(List<MultipartFile> multipartFiles, Long productId){
        var product = productRepository.findByIdOrThrow(productId);
        List<String> fileNameList = new ArrayList<>();
        multipartFiles.forEach(file -> {
            validateFile(file);
            String filePath = createFilePath(file);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());
            try(InputStream inputStream = file.getInputStream()){
                amazonS3.putObject(new PutObjectRequest(bucket, filePath, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            }catch (IOException e){
                throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR, "파일 쓰기에 실패하였습니다.");
            }
            fileNameList.add(filePath);
        });
        List<ProductImage> productImages = fileNameList.stream()
            .map(fileName -> ProductImage.builder().product(product).base(fileName).build())
            .toList();
        productImageRepository.saveAll(productImages);
        return fileNameList;
    }

    public ByteArrayResource download(String fileKey){
        S3Object fullObject = null;
        byte[] bytes;
        try{
            fullObject = amazonS3.getObject(new GetObjectRequest(bucket, s3Path + fileKey));
            S3ObjectInputStream objectContent = fullObject.getObjectContent();
            bytes = IOUtils.toByteArray(objectContent);
        }catch (Exception e){
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR, e.getMessage());
        }
        return new ByteArrayResource(bytes);
    }

    public void deleteFile(String fileName, Long productImageId){
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
        productImageRepository.deleteById(productImageId);
    }

    private void validateFile(MultipartFile file){
        validateFileName(file.getOriginalFilename());
        validateFileSize(file.getSize());
    }

    private void validateFileName(String originalFilename) {
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE, "파일 이름이 유효하지 않음");
        }
    }

    private void validateFileSize(long fileSize) {
        if (fileSize == 0) {
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR, "유효하지 않은 파일 크기 입니다.");
        }
    }

    private String createFilePath(MultipartFile file){
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(s3Path).append(hashFile(file.getBytes())).append(getFileExtension(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR, "파일 해싱에 실패하였습니다.");
        }
        return sb.toString();
    }

    private String getFileExtension(String fileName){
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        }catch (StringIndexOutOfBoundsException e){
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR, "잘못된 형식의 파일(" + fileName + ")");
        }
    }

    private String hashFile(byte[] fileData) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(fileData);
            return bytesToHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR, e.getMessage());
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
