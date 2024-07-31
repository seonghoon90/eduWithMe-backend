package com.sparta.eduwithme.domain.comment.dto;

import com.sparta.eduwithme.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private final Long commentId;
    private final String nickName;
    private final String comment;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.nickName = comment.getUserNickname();
        this.comment = comment.getComment();
        this.createdAt = comment.getUpdatedAt();
        this.updatedAt = comment.getCreatedAt();
    }
}
