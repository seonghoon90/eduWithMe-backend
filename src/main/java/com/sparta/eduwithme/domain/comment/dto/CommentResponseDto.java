package com.sparta.eduwithme.domain.comment.dto;

import com.sparta.eduwithme.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final String nickName;
    private final String comment;
    private final LocalDateTime updatedAt;

    public CommentResponseDto(Comment comment) {
        this.nickName = comment.getUserNickname();
        this.comment = comment.getComment();
        this.updatedAt = comment.getUpdatedAt();
    }
}
