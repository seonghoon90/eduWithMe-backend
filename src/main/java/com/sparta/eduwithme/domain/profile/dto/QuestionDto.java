package com.sparta.eduwithme.domain.profile.dto;

import com.sparta.eduwithme.domain.question.entity.Category;
import com.sparta.eduwithme.domain.question.entity.Difficulty;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
public class QuestionDto {
    private final Long questionId;
    private final String category;
    private final String title;
    private final String difficulty;
    private final String formattedCreatedAt;
    private final String formattedUpdatedAt;
    private final String roomName;
    private final Long orderInRoom;

    public QuestionDto(Long questionId, Category category, String title, Difficulty difficulty,
                       LocalDateTime formattedCreatedAt, LocalDateTime formattedUpdatedAt, String roomName, Long orderInRoom) {
        this.questionId = questionId;
        this.category = category.getCategoryName();
        this.title = title;
        this.difficulty = difficulty.getLevel();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime koreaCreateAtTime = formattedCreatedAt.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
        this.formattedCreatedAt = koreaCreateAtTime.format(formatter);

        LocalDateTime koreaUpdateAtTime = formattedUpdatedAt.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("Asia/Seoul"))
                .toLocalDateTime();
        this.formattedUpdatedAt = koreaUpdateAtTime.format(formatter);

        this.roomName = roomName;
        this.orderInRoom = orderInRoom;
    }
}