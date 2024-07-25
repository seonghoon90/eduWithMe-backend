package com.sparta.eduwithme.domain.room.dto;

import com.sparta.eduwithme.domain.room.entity.Student;
import lombok.Getter;

@Getter
public class StudentResponseDto {

    private final Long userId;
    private final Long roomId;
    private final String nickName;
    private final String roomName;
    private final Long managerUserId;

    public StudentResponseDto(Student student) {
        this.userId = student.getUser().getId();
        this.roomId = student.getRoom().getId();
        this.nickName = student.getUser().getNickName();
        this.roomName = student.getRoom().getRoomName();
        this.managerUserId = student.getRoom().getManagerUserId();
    }

}
