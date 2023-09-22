package com.github.msafriends.serviceproduct.modulecore.dto.productimage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Getter;

public class CustomMultipartFile implements MultipartFile {
    private final byte[] bytes;
    private final String name;
    private final String originalFileName;
    private final String contentType;
    private final boolean isEmpty;
    private final long size;
    @Getter
    private final int width;

    @Builder
    public CustomMultipartFile(byte[] bytes, int width, String originalFileName, String name, String contentType, long size) {
        this.bytes = bytes;
        this.width = width;
        this.name = name;
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.isEmpty = false;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFileName;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return isEmpty;
    }

    @Override
    public long getSize() {
        return size;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return bytes;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(this.bytes);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        FileCopyUtils.copy(this.bytes, dest);
    }
}
