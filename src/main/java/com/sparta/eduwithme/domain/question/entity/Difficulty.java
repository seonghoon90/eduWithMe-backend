package com.sparta.eduwithme.domain.question.entity;

import lombok.Getter;

@Getter
public enum Difficulty {
    LEVEL_ONE("1"),
    LEVEL_TWO("2"),
    LEVEL_THREE("3"),
    LEVEL_FOUR("4"),
    LEVEL_FIVE("5");

    private final String level;

    Difficulty(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return this.level;
    }


}
