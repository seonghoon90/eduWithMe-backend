package com.sparta.eduwithme.domain.profile.dto;

import com.sparta.eduwithme.domain.question.entity.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SolvedQuestionDto {
    private Long questionNo;
    private String category;
    private String title;
    private Difficulty difficulty;
    private LocalDateTime createdAt;
}
