package com.github.msafriends.serviceproduct.modulecore.domain.productimage;

import lombok.Getter;

@Getter
public enum ResizedPixel {


    SIZE_100(100),
    SIZE_110(110),
    SIZE_120(120),
    SIZE_130(130),
    SIZE_140(140),
    SIZE_150(150),
    SIZE_170(170),
    SIZE_200(200),
    SIZE_250(250),
    SIZE_270(270),
    SIZE_300(300);

    private final int pixelSize;
    private final String pixelKey;
    private final String nameSuffix;
    private final String pathPrefix;
    ResizedPixel(int pixelSize) {
        this.pixelSize = pixelSize;
        this.pixelKey = String.valueOf(pixelSize);
        this.nameSuffix = "_" + pixelSize;
        this.pathPrefix = "/" + pixelSize + "/";
    }
}
