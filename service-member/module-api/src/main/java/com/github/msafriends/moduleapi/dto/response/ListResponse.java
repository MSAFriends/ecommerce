package com.github.msafriends.moduleapi.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ListResponse<T> {
    private int valueCount;
    private List<T> values = new ArrayList<>();

    public ListResponse(int valueCount, List<T> values) {
        this.valueCount = valueCount;
        this.values = values;
    }
}
