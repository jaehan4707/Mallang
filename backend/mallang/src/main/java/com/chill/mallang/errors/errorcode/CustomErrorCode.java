package com.chill.mallang.errors.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode{
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Cannot find resource"),
    // 변서원바보(실패,"바보")
    NICKNAME_IS_EXISTS(HttpStatus.CONFLICT, "Nickname already exists"),
    EMAIL_IS_EXISTS(HttpStatus.CONFLICT, "email already exists")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
