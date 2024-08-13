package com.sparta.eduwithme.domain.chat.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
public class ChatMessageResponse {
    private final String content;
    private final String sender;
    private final String photoUrl;
    private final String timestamp;

    public ChatMessageResponse(String content, String sender, String photoUrl, LocalDateTime timestamp) {
        this.content = content;
        this.sender = sender;
        LocalDateTime koreaTime = timestamp.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.timestamp = koreaTime.format(formatter);
        this.photoUrl = photoUrl;
    }

}
