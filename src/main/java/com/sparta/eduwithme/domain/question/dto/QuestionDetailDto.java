package com.sparta.eduwithme.domain.question.dto;

import com.sparta.eduwithme.domain.question.entity.Question;
import lombok.Getter;

@Getter
public class QuestionDetailDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String category;
    private final String difficulty;
    private final Long point;
    private final Integer orderInRoom;
    private final AnswerOptionDto answerOption;

    public QuestionDetailDto(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.category = question.getCategory().getCategoryName();
        this.difficulty = question.getDifficulty().getLevel();
        this.point = question.getPoint();
        this.orderInRoom = question.getOrderInRoom();
        this.answerOption = new AnswerOptionDto(question.getAnswer());
    }
}
