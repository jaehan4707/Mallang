package com.chill.mallang.domain.quiz.dto;

public class SucessDTO {
    private int status = 200;
    private String httpstatuscode = "Success";
    private String message;

    SucessDTO ( String message){
        this.message = message;
    }
}