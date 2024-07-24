package com.sparta.eduwithme.domain.question.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnswerRequestDto {
    private String first;
    private String second;
    private String third;
    private String fourth;
    private int answered;

    public AnswerRequestDto(String first, String second, String third, String fourth, int answered) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.answered = answered;
    }
}
