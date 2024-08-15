package com.chill.mallang.domain.area.service;

import com.chill.mallang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AreaErrorCode implements ErrorCode {
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Cannot find resource"),
    INVALID_TEAM(HttpStatus.BAD_REQUEST,"teamId는 1또는 2입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}