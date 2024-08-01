package com.sparta.eduwithme.domain.room.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RoomWithNickNameDto {
    private Long id;
    private String roomName;
    private String roomPassword;
    private Long managerUserId;
    private String nickName;
}