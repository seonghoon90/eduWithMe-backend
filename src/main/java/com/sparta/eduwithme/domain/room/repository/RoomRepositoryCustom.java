package com.sparta.eduwithme.domain.room.repository;

import com.sparta.eduwithme.domain.room.dto.RoomWithNickNameDto;

import java.util.List;

public interface RoomRepositoryCustom {
    List<RoomWithNickNameDto> getRoomListWithPage(long page, int pageSize);
}
