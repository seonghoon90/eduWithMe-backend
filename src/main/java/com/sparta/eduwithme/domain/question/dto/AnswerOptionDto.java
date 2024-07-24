package com.sparta.eduwithme.domain.question.dto;

import com.sparta.eduwithme.domain.question.entity.Answer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerOptionDto {
    private Long id;
    private String first;
    private String second;
    private String third;
    private String fourth;

    public AnswerOptionDto(Answer answer) {
        this.id = answer.getId();
        this.first = answer.getFirst();
        this.second = answer.getSecond();
        this.third = answer.getThird();
        this.fourth = answer.getFourth();
    }
}
