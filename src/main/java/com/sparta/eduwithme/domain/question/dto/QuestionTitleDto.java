package com.sparta.eduwithme.domain.question.dto;

import com.sparta.eduwithme.domain.question.entity.Category;
import com.sparta.eduwithme.domain.question.entity.Difficulty;
import com.sparta.eduwithme.domain.question.entity.Question;
import lombok.Getter;

@Getter
public class QuestionTitleDto {
    private Long questionid;
    private String title;
    private Category category;
    private Difficulty difficulty;
    private Long point;

    public QuestionTitleDto(Question question) {
        this.questionid = question.getId();
        this.title = question.getTitle();
        this.category = question.getCategory();
        this.difficulty = question.getDifficulty();
        this.point = question.getPoint();
    }
}