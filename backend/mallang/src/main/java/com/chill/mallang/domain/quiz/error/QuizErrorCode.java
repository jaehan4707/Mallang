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
    QUIZ_ID_NULL(HttpStatus.BAD_REQUEST, "Quiz ID must not be null"),
    USER_ID_NULL(HttpStatus.BAD_REQUEST, "User ID must not be null"),
    AREA_ID_NULL(HttpStatus.BAD_REQUEST, "Area ID must not be null"),
    FACTION_ID_NULL(HttpStatus.BAD_REQUEST, "Faction ID must not be null"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
