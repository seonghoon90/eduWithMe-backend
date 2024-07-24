package com.sparta.eduwithme.domain.room.dto;

import com.sparta.eduwithme.domain.room.entity.Room;
import lombok.Getter;

@Getter
public class DetailRoomResponseDto {
    private final Long roomId;
    private final String roomName;
    private final Long managerUserId;
    private final boolean roomPasswordCheck;

    public DetailRoomResponseDto(Room room, boolean roomPasswordCheck) {
        this.roomId = room.getId();
        this.roomName = room.getRoomName();
        this.managerUserId = room.getManagerUserId();
        this.roomPasswordCheck = roomPasswordCheck;
    }
}
