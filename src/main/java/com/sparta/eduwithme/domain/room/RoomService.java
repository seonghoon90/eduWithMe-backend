package com.sparta.eduwithme.domain.room;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.room.dto.CreateRoomRequestDto;
import com.sparta.eduwithme.domain.room.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    // Student 테이블 생성 후 한 방에 소속 된 총 인원수 조회해서 같이 리턴 해야함.
    public void createRoom(CreateRoomRequestDto requestDto, Long managerUserId) {
        boolean isDuplicateRoomName = roomRepository.findByRoomName(requestDto.getRoomName()).isPresent();
        if(isDuplicateRoomName) {
            throw new CustomException(ErrorCode.SAME_NEW_ROOM_NAME);
        }
        Long countRooms = roomRepository.countByManagerUserId(managerUserId);
        if(countRooms >= 2) {
            throw new CustomException(ErrorCode.CAN_NOT_MADE_ROOM);
        }
        Room room = Room.builder()
                .roomName(requestDto.getRoomName())
                .roomPassword(requestDto.getRoomPassword())
                .managerUserId(managerUserId).build();
        roomRepository.save(room);
    }
}
