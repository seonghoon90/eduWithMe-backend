package com.sparta.eduwithme.domain.question.dto;

import com.sparta.eduwithme.domain.question.entity.Category;
import com.sparta.eduwithme.domain.question.entity.Difficulty;
import com.sparta.eduwithme.domain.question.entity.Question;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QuestionTitleDto {
    private Long questionId;
    private String title;
    private Category category;
    private Difficulty difficulty;
    private Long point;
    private final LocalDateTime updatedAt;

    public QuestionTitleDto(Question question) {
        this.questionId = question.getId();
        this.title = question.getTitle();
        this.category = question.getCategory();
        this.difficulty = question.getDifficulty();
        this.point = question.getPoint();
        this.updatedAt = question.getUpdatedAt();
    }
}