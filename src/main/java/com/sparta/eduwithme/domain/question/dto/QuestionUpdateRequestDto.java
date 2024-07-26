package com.sparta.eduwithme.domain.question.dto;

import com.sparta.eduwithme.domain.question.entity.Category;
import com.sparta.eduwithme.domain.question.entity.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class QuestionUpdateRequestDto {
    private String title;
    private String content;
    private Category category;
    private Difficulty difficulty;
    private AnswerUpdateRequestDto answer;
    private Long point;

    public QuestionUpdateRequestDto(String title, String content, Category category, Difficulty difficulty, AnswerUpdateRequestDto answer, Long point) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.difficulty = difficulty;
        this.answer = answer;
        this.point = point;
    }
}
