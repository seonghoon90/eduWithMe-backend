package com.sparta.eduwithme.domain.room.entity;

import com.sparta.eduwithme.common.TimeStamp;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Table(name = "rooms")
@NoArgsConstructor @Getter
public class Room extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;

    private String roomPassword;

    private Long managerUserId;

    @Builder
    public Room(String roomName, String roomPassword, Long managerUserId) {
        this.roomName = roomName;
        this.roomPassword = roomPassword;
        this.managerUserId = managerUserId;
    }

}