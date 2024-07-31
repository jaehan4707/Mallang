package com.chill.mallang.domain.quiz.error;

import com.chill.mallang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum QuizErrorCode implements ErrorCode {
    INVALID_QUIZ_PK(HttpStatus.BAD_REQUEST, "해당 Quiz PK로 값을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "Cannot find user"),
    QUIZ_NOT_FOUND(HttpStatus.NOT_FOUND, "Cannot find quiz"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
