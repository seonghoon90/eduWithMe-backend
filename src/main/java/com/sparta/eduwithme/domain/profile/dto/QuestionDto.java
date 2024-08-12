package com.sparta.eduwithme.domain.profile.dto;

import com.sparta.eduwithme.domain.question.entity.Category;
import com.sparta.eduwithme.domain.question.entity.Difficulty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestionDto {
    private final Long questionId;
    private final String category;
    private final String title;
    private final String difficulty;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String roomName;
    private final Long orderInRoom;

    public QuestionDto(Long questionId, Category category, String title, Difficulty difficulty,
                       LocalDateTime createdAt, LocalDateTime updatedAt, String roomName, Long orderInRoom) {
        this.questionId = questionId;
        this.category = category.getCategoryName();
        this.title = title;
        this.difficulty = difficulty.getLevel();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roomName = roomName;
        this.orderInRoom = orderInRoom;
    }
}