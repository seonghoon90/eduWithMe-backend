package com.sparta.eduwithme.domain.question.dto;

import com.sparta.eduwithme.domain.question.entity.Category;
import com.sparta.eduwithme.domain.question.entity.Difficulty;
import com.sparta.eduwithme.domain.question.entity.Question;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionDetailDto {
    private Long id;
    private String title;
    private String content;
    private Category category;
    private Difficulty difficulty;
    private Long point;
    private List<AnswerOptionDto> answerOptions;

    public QuestionDetailDto(Question question) {
        this.id = question.getId();
        this.title = question.getTitle();
        this.content = question.getContent();
        this.category = question.getCategory();
        this.difficulty = question.getDifficulty();
        this.point = question.getPoint();
        this.answerOptions = question.getAnswers().stream()
                .map(AnswerOptionDto::new)
                .toList();
    }
}
