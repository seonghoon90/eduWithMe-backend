package com.sparta.eduwithme.domain.room.dto;

import com.sparta.eduwithme.domain.room.entity.Student;
import lombok.Getter;

@Getter
public class RoomUserListResponseDto {
    private final String roomName;
    private final String nickName;
    private final Long managerUserId;
    private final Long userId;

    public RoomUserListResponseDto(Student student) {
        this.roomName = student.getRoom().getRoomName();
        this.nickName = student.getUser().getNickName();
        this.managerUserId = student.getRoom().getManagerUserId();
        this.userId = student.getUser().getId();
    }
}
