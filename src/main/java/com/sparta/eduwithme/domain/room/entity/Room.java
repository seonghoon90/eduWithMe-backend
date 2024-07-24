package com.sparta.eduwithme.domain.room.entity;

import com.sparta.eduwithme.common.TimeStamp;
import com.sparta.eduwithme.domain.question.entity.Question;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "rooms")
@Entity
@Getter
@NoArgsConstructor
public class Room extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomName;

    private String roomPassword;

    private Long managerUserId;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions;

    @Builder
    public Room(String roomName, String roomPassword, Long managerUserId) {
        this.roomName = roomName;
        this.roomPassword = roomPassword;
        this.managerUserId = managerUserId;
    }

    public void updateRoomName(String roomName) {
        this.roomName = roomName;
    }

}