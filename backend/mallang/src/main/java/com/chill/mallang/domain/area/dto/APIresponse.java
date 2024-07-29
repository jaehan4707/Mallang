package com.chill.mallang.domain.area.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class APIresponse<T> {
    private final int status;
    private final String message;
    private final T data; // 데이터 들어갈 곳. 제네릭 타입.
}
