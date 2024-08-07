package com.sparta.eduwithme.domain.chat.entity;

import com.sparta.eduwithme.common.TimeStamp;
import com.sparta.eduwithme.domain.room.entity.Room;
import com.sparta.eduwithme.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "chats")
@Entity
@Getter
@NoArgsConstructor
public class Chat extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Builder
    public Chat(String content, User user, Room room) {
        this.content = content;
        this.user = user;
        this.room = room;
    }
}
