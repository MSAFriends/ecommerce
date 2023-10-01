package com.github.msafriends.serviceproduct.moduleapi.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import com.github.msafriends.serviceproduct.modulecore.domain.productimage.ResizedPixel;
import com.github.msafriends.serviceproduct.modulecore.exception.ErrorCode;
import com.github.msafriends.serviceproduct.modulecore.exception.FileProcessingException;
import com.github.msafriends.serviceproduct.modulecore.exception.InvalidStateException;

public class ImageUtils {
    public static final String BASE_PATH_PREFIX = "/base/";
    public static final String DOT = ".";
    public static final String COMPRESSED_FILE_NAME_SUFFIX = "_compressed";
    public static final String BASE_IMAGE_KEY = "base";
    public static final String FILE_NAME_HASHING_ALGORITHM = "SHA-256";

    private ImageUtils(){
        throw new InvalidStateException(ErrorCode.INSTANTIATE_UTIL_CLASS_ERROR);
    }



    public static String createResizedFilePath(String s3Path, ResizedPixel pixelSize, String fileName, String fileExtention){
        return s3Path + pixelSize.getPathPrefix() + fileName + pixelSize.getNameSuffix() + DOT + fileExtention;
    }

    public static String createFileNameWithPixelAndExtension(String hashedName, String fileExtension, ResizedPixel pixel){
        return hashedName + pixel.getNameSuffix() + DOT + fileExtension;
    }

    public static String createBaseFilePath(String s3Path, String fileName, String fileExtention){
        StringBuilder sb = new StringBuilder();
        sb.append(s3Path).append(BASE_PATH_PREFIX).append(fileName).append(DOT).append(fileExtention);
        return sb.toString();
    }

    public static String createCompressedBaseFileName(String fileName, String fileExtention){
        StringBuilder sb = new StringBuilder();
        sb.append(fileName).append(COMPRESSED_FILE_NAME_SUFFIX).append(DOT).append(fileExtention);
        return sb.toString();
    }

    public static String createCompressedBaseFilePath(String s3Path, String fileName, String fileExtention){
        StringBuilder sb = new StringBuilder();
        sb.append(s3Path).append(BASE_PATH_PREFIX).append(fileName).append(COMPRESSED_FILE_NAME_SUFFIX).append(DOT).append(fileExtention);
        return sb.toString();
    }
  
    public static String getFileExtension(String fileName){
        try {
            return fileName.substring(fileName.lastIndexOf(DOT) + 1);
        }catch (StringIndexOutOfBoundsException e){
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR, "잘못된 형식의 파일(" + fileName + ")");
        }
    }

    public static String hashFile(byte[] fileData) {
        try {
            MessageDigest md = MessageDigest.getInstance(FILE_NAME_HASHING_ALGORITHM);
            byte[] hashedBytes = md.digest(fileData);
            return bytesToHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new FileProcessingException(ErrorCode.FILE_PROCESSING_ERROR, e.getMessage());
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder(2 * bytes.length);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static byte[] compressFile(byte[] data){
        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)){
            byte[] tmp = new byte[4 * 1024];
            while (!deflater.finished()){
                int size = deflater.deflate(tmp);
                outputStream.write(tmp, 0, size);
            }
            return outputStream.toByteArray();
        }catch (IOException ex){
            throw new FileProcessingException(ErrorCode.FILE_COMPRESSING_ERROR);
        }
    }

    public static byte[] decompressImage(byte[] data){
        Inflater inflater = new Inflater();
        inflater.setInput(data);

        byte[] tmp = new byte[4 * 1024];
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)){
            while (!inflater.finished()){
                int size = inflater.inflate(tmp);
                outputStream.write(tmp, 0, size);
            }
            return outputStream.toByteArray();
        }catch (Exception ex){
            throw new FileProcessingException(ErrorCode.FILE_DECOMPRESSING_ERROR);
        }
    }
}
