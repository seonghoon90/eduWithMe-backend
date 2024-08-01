package com.sparta.eduwithme.domain.question.dto;

import com.sparta.eduwithme.domain.question.entity.Category;
import com.sparta.eduwithme.domain.question.entity.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class QuestionRequestDto {
    private String title;
    private String content;
    private Category category;
    private Difficulty difficulty;
    private AnswerRequestDto answer;

    public QuestionRequestDto(String title, String content, Category category, Difficulty difficulty, AnswerRequestDto answer) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.difficulty = difficulty;
        this.answer = answer;
    }
}
