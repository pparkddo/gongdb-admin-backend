package com.gongdb.admin.global.dto;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleMessageResponse {
    
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;

    private SimpleMessageResponse(String message) {
        this.message = message;
    }

    public static SimpleMessageResponse of(String message) {
        return new SimpleMessageResponse(message);
    }
}
