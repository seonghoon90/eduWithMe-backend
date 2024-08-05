package com.sparta.eduwithme.domain.comment.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CommentRoomDto {
    private Long commentId;
    private String nickName;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String roomName;
    private Integer questionOrderInRoom;
}