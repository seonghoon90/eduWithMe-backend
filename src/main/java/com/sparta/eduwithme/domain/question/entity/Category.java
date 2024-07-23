package com.sparta.eduwithme.domain.question.entity;

import lombok.Getter;

@Getter
public enum Category {
    MATH("수학"),
    SCIENCE("과학"),
    ENGLISH("영어"),
    KOREAN("국어");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return this.categoryName;
    }


    }
