package com.sparta.eduwithme.domain.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnswerResultDto {
    private boolean correct;
    private Long earnedPoints;
    private String message;
}
