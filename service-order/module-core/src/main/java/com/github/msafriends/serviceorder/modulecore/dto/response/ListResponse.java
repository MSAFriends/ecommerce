package com.github.msafriends.serviceorder.modulecore.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListResponse<T> {
    private int valueCount;
    private List<T> values;

    public ListResponse(int valueCount, List<T> values) {
        this.valueCount = valueCount;
        this.values = values;
    }
}
