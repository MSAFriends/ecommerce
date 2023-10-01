package com.github.msafriends.serviceproduct.moduleapi.service.productimage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.github.msafriends.serviceproduct.moduleapi.utils.ImageUtils;
import com.github.msafriends.serviceproduct.modulecore.domain.productimage.ProductImage;
import com.github.msafriends.serviceproduct.modulecore.domain.productimage.ResizedPixel;
import com.github.msafriends.serviceproduct.modulecore.dto.productimage.CustomMultipartFile;
import com.github.msafriends.serviceproduct.modulecore.dto.productimage.ProductImageResponse;
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
@Transactional(readOnly = true)
public class AwsS3Service {


    @Value("${images.path}")
    private String s3Path;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;


    @Transactional
    public ProductImageResponse uploadFile(MultipartFile multipartFile, Long productId){
        var product = productRepository.findByIdOrThrow(productId);
        Map<String, String> fileNameMap = new HashMap<>();
        validateFile(multipartFile);
        try{
            byte[] originalFile = multipartFile.getBytes();
            var hashedName = ImageUtils.hashFile(originalFile);
            var fileExtension = ImageUtils.getFileExtension(multipartFile.getOriginalFilename());
            var baseFilePath = ImageUtils.createBaseFilePath(s3Path, hashedName, fileExtension);
            var compressedBaseFilePath = ImageUtils.createCompressedBaseFilePath(s3Path, hashedName);
            MultipartFile baseImageToSend = new MockMultipartFile(baseFilePath,
                ImageUtils.compressFile(originalFile));
            sendFileToS3(baseImageToSend, compressedBaseFilePath);
            fileNameMap.put(ImageUtils.BASE_IMAGE_KEY, ImageUtils.createCompressedBaseFileName(hashedName));
            List<CustomMultipartFile> resizedImages = createResizedImages(multipartFile, hashedName, fileExtension);
            resizedImages.forEach(resizedImage -> sendFileToS3(resizedImage, resizedImage.getName()));
            resizedImages.forEach(image -> fileNameMap.put(String.valueOf(image.getWidth()), image.getOriginalFilename()));
        } catch (IOException ex){
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR);
        }
        var productImage = ProductImage.createProductImageWithResize(product, fileNameMap);
        var savedImage = productImageRepository.save(productImage);
        return ProductImageResponse.from(savedImage);
    }

    private void sendFileToS3(MultipartFile file, String filePath) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());
        try(InputStream inputStream = file.getInputStream()){
            amazonS3.putObject(new PutObjectRequest(
                bucket,
                filePath,
                inputStream,
                objectMetadata)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException ex){
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR);
        }
    }

    public ByteArrayResource download(String fileKey){
        S3Object fullObject = null;
        byte[] bytes;
        byte[] decompressed;
        try{
            fullObject = amazonS3.getObject(new GetObjectRequest(bucket, s3Path + fileKey));
            S3ObjectInputStream objectContent = fullObject.getObjectContent();
            bytes = IOUtils.toByteArray(objectContent);
            decompressed = ImageUtils.decompressImage(bytes);
        }catch (Exception e){
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR, e.getMessage());
        }
        return new ByteArrayResource(decompressed);
    }

    @Transactional
    public void deleteFile(String fileName, Long productImageId){
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
        productImageRepository.deleteById(productImageId);
    }

    public List<CustomMultipartFile> createResizedImages(MultipartFile file, String fileName, String fileExtension) throws
        IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());
        return Arrays.stream(ResizedPixel.values())
            .filter(pixel -> pixel.getPixelSize() < image.getWidth())
            .map(pixel -> resizer(fileName, fileExtension, image, pixel, s3Path)).toList();
    }



    public CustomMultipartFile resizer(String fileName, String fileExtension, BufferedImage originalImage, ResizedPixel pixel, String s3Path){
        try {
            int originalWidth = originalImage.getWidth();
            int originalHeight = originalImage.getHeight();
            int newWidth = pixel.getPixelSize();
            int newHeight = pixel.getPixelSize() * originalHeight / originalWidth;
            BufferedImage resizedImage = Thumbnailator.createThumbnail(originalImage, newWidth, newHeight);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, fileExtension, out);
            out.flush();
            return CustomMultipartFile.builder()
                .bytes(out.toByteArray())
                .originalFileName(ImageUtils.createFileNameWithPixelAndExtension(fileName, fileExtension, pixel))
                .name(ImageUtils.createResizedFilePath(s3Path, pixel, fileName, fileExtension))
                .size(out.size())
                .contentType(fileExtension)
                .width(pixel.getPixelSize())
                .build();
        } catch (IOException ex){
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR);
        }
    }

    private void validateFile(MultipartFile file){
        validateFileContentType(file.getContentType());
        validateFileName(file.getOriginalFilename());
        validateFileSize(file.getSize());
    }

    private void validateFileContentType(String contentType){
        if(!contentType.contains("image")){
            throw new InvalidValueException(ErrorCode.INVALID_INPUT_VALUE, "파일 형식이 유효하지 않습니다.");
        }
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


}
