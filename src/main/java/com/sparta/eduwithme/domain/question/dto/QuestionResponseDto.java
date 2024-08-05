package com.sparta.eduwithme.domain.question.dto;

import com.sparta.eduwithme.domain.question.entity.Question;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestionResponseDto {

    private final Long questionId;
    private final String title;
    private final String content;
    private final String category;
    private final String difficulty;
    private final Long point;
    private final Integer orderInRoom;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public QuestionResponseDto(Question question) {
        this.questionId = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.category = question.getCategory().getCategoryName();
        this.difficulty = question.getDifficulty().getLevel();
        this.point = question.getPoint();
        this.orderInRoom = question.getOrderInRoom();
        this.createdAt = question.getCreatedAt();
        this.updatedAt = question.getUpdatedAt();
    }

}
