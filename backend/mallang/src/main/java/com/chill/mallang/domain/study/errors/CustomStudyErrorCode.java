package com.chill.mallang.domain.study.errors;

import com.chill.mallang.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomStudyErrorCode implements ErrorCode {
    WORDMEAN_IS_NOT_FOUND(HttpStatus.NOT_FOUND, "WordMean not found"),
    WORD_IS_SOLD_OUT(HttpStatus.NO_CONTENT, "No unused word means available."),
    NOT_WRONG_WORD(HttpStatus.NOT_FOUND,"This user didn't get that wrong."),
    WRONG_STUDYID(HttpStatus.NOT_FOUND, "Wrong studyId"),
    STUDYID_IS_NULL(HttpStatus.NOT_FOUND, "Study id is null"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
