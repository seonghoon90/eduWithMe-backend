package com.sparta.eduwithme.domain.comment.dto;

import com.sparta.eduwithme.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long commentId;
    private final Long userId;
    private final String nickName;
    private final String comment;
    private final String formattedCreatedAt;
    private final String formattedUpdatedAt;
    private final Long questionOrderInRoom;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.userId = comment.getUser().getId();
        this.nickName = comment.getUserNickname();
        this.comment = comment.getComment();
        this.formattedCreatedAt = comment.getFormattedCreatedAt();
        this.formattedUpdatedAt = comment.getFormattedUpdatedAt();
        this.questionOrderInRoom = comment.getQuestion().getOrderInRoom();
    }
}
