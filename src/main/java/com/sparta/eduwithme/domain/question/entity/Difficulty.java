package com.sparta.eduwithme.domain.question.entity;

import lombok.Getter;

@Getter
public enum Difficulty {
    LEVEL_ONE("1", 10L),
    LEVEL_TWO("2",20L),
    LEVEL_THREE("3",30L),
    LEVEL_FOUR("4",40L),
    LEVEL_FIVE("5",50L);

    private final String level;
    private final Long point;

    Difficulty(String level, Long point) {
        this.level = level;
        this.point = point;
    }

    public Long getPoint() {
        return this.point;
    }

    @Override
    public String toString() {
        return this.level;
    }


}
