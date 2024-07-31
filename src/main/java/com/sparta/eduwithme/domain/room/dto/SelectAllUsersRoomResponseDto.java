package com.sparta.eduwithme.domain.room.dto;

import com.sparta.eduwithme.domain.room.entity.Student;
import lombok.Getter;

@Getter
public class SelectAllUsersRoomResponseDto {

    private final String roomName;
    private final Long managerUserId;
    private final Long userId;
    private final Long roomId;
    private final String roomPassword;

    public SelectAllUsersRoomResponseDto(Student student) {
        this.roomName = student.getRoom().getRoomName();
        this.managerUserId = student.getUser().getId();
        this.userId = student.getUser().getId();
        this.roomId = student.getRoom().getId();
        this.roomPassword = student.getRoom().getRoomPassword();
    }
}
