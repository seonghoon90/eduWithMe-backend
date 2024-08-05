package com.sparta.eduwithme.domain.profile.dto;

import com.sparta.eduwithme.domain.question.entity.Category;
import com.sparta.eduwithme.domain.question.entity.Difficulty;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestionDto {
    private Long questionId;
    private String category;
    private String title;
    private String difficulty;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String roomName;
    private final Integer orderInRoom;

    public QuestionDto(Long questionId, Category category, String title, Difficulty difficulty,
                       LocalDateTime createdAt, LocalDateTime updatedAt, String roomName, Integer orderInRoom) {
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