package com.sparta.eduwithme.domain.question.entity;

import lombok.Getter;

@Getter

public enum QuestionType {

    wrong("오답"),
    solve("정답");

    private final String questionStatus;

    QuestionType(String questionStatus) {
        this.questionStatus = questionStatus;
    }

    @Override
    public String toString() {
        return this.questionStatus;
    }
}
