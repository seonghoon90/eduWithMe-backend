package com.sparta.eduwithme.domain.chat.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ChatMessageResponse {
    private final String content;
    private final String sender;
    private final String photoUrl;
    private final LocalDateTime timestamp;

    public ChatMessageResponse(String content, String sender, String photoUrl, LocalDateTime timestamp) {
        this.content = content;
        this.sender = sender;
        this.timestamp = LocalDateTime.parse(timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        this.photoUrl = photoUrl;
    }

}
