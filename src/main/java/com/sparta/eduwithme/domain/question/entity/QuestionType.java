package com.sparta.eduwithme.domain.question.entity;

import lombok.Getter;

@Getter

public enum QuestionType {

    WRONG("오답"),
    SOLVE("정답");

    private final String questionStatus;

    QuestionType(String questionStatus) {
        this.questionStatus = questionStatus;
    }

    @Override
    public String toString() {
        return this.questionStatus;
    }
}
