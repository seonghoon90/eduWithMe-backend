package com.sparta.eduwithme.domain.room.repository;

import com.sparta.eduwithme.domain.room.dto.RoomWithNickNameDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoomRepositoryCustom {
    Page<RoomWithNickNameDto> getRoomListWithPage(Pageable pageable);
}