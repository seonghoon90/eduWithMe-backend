package com.sparta.eduwithme.domain.comment.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
public class CommentRoomDto {
    private final Long commentId;
    private final String nickName;
    private final String comment;
    private final String formattedCreatedAt;
    private final String formattedUpdatedAt;
    private final String roomName;
    private final Long questionOrderInRoom;

    public CommentRoomDto(Long commentId, String nickName, String comment, LocalDateTime updatedAt,
                          LocalDateTime createdAt, String roomName, Long questionOrderInRoom) {

        this.commentId = commentId;
        this.nickName = nickName;
        this.comment = comment;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime koreaCreateAtTime = createdAt.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
        this.formattedCreatedAt = koreaCreateAtTime.format(formatter);

        LocalDateTime koreaUpdateAtTime = updatedAt.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();

        this.formattedUpdatedAt = koreaUpdateAtTime.format(formatter);
        this.roomName = roomName;
        this.questionOrderInRoom = questionOrderInRoom;
    }
}