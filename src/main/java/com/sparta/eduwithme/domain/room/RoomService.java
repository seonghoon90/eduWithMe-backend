package com.sparta.eduwithme.domain.room;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.room.dto.CreateRoomRequestDto;
import com.sparta.eduwithme.domain.room.entity.Room;
import com.sparta.eduwithme.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private static final int ROOM_CREATE_LIMIT = 2;

    public void createRoom(CreateRoomRequestDto requestDto, User user) {
        boolean isDuplicateRoomName = roomRepository.findByRoomName(requestDto.getRoomName()).isPresent();
        if(isDuplicateRoomName) {
            throw new CustomException(ErrorCode.SAME_NEW_ROOM_NAME);
        }
        Long countRooms = roomRepository.countByManagerUserId(user.getId());
        if(countRooms >= ROOM_CREATE_LIMIT) {
            throw new CustomException(ErrorCode.CAN_NOT_MADE_ROOM);
        }
        Room room = Room.builder()
                .roomName(requestDto.getRoomName())
                .roomPassword(requestDto.getRoomPassword())
                .managerUserId(user.getId()).build();
        roomRepository.save(room);
    }

    @Transactional(readOnly = true)
    public Room findById(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(ErrorCode.ROOM_NOT_FOUND)
        );
    }
}
