package com.chill.mallang.domain.quiz.dto.core;

import lombok.Data;

@Data
public class Message {

    private String role;
    private String content; // prompt

    public Message(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
