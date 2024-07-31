package com.sparta.eduwithme.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionDto {
    private Long questionNo;
    private String category;
    private String title;
    private String difficulty;
    private String createdAt;
}
