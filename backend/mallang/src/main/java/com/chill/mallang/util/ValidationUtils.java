package com.chill.mallang.util;
import com.chill.mallang.errors.errorcode.ErrorCode;
import com.chill.mallang.errors.exception.RestApiException;

public class ValidationUtils {

    public static <T> T requireNonNull(T obj, ErrorCode errorCode) {
        if(obj == null){
            throw new RestApiException(errorCode);
        }
        return obj;
    }
}
