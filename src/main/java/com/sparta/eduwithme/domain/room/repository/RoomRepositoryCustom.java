package com.sparta.eduwithme.domain.room.repository;

import com.sparta.eduwithme.domain.room.entity.Room;

import java.util.List;

public interface RoomRepositoryCustom {
    List<Room> getRoomListWithPage(long page, int pageSize);
}
