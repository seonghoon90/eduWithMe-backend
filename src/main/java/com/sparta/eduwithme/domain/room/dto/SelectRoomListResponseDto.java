package com.sparta.eduwithme.domain.room.dto;

import com.sparta.eduwithme.domain.room.entity.Room;
import lombok.Getter;

@Getter
public class SelectRoomListResponseDto {
    private final String roomName;
    private final String roomPassword;
    private final Long managerUserId;

    public SelectRoomListResponseDto(Room room) {
        this.roomName = room.getRoomName();
        this.roomPassword = room.getRoomPassword();
        this.managerUserId = room.getManagerUserId();
    }
}
