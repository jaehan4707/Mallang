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
    EMAIL_IS_EXISTS(HttpStatus.CONFLICT, "email already exists"),
    JOIN_IS_FAILED(HttpStatus.CONFLICT, "join is failed"),
    AUTHENTICATED_FAILED(HttpStatus.NON_AUTHORITATIVE_INFORMATION, "Authenticated failed"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method not allowed"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
