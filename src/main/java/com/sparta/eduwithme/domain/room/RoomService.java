package com.sparta.eduwithme.domain.room;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.room.dto.CreateRoomRequestDto;
import com.sparta.eduwithme.domain.room.dto.SelectRoomListResponseDto;
import com.sparta.eduwithme.domain.room.dto.UpdateRequestDto;
import com.sparta.eduwithme.domain.room.entity.Room;
import com.sparta.eduwithme.domain.room.repository.RoomRepository;
import com.sparta.eduwithme.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    private static final int ROOM_CREATE_LIMIT = 2;
    private static final int pageSize = 5;

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

    public List<SelectRoomListResponseDto> getRoomListWithPage(int page) {
        PageRequest pageRequest = PageRequest.of(page, pageSize);
        return roomRepository.getRoomListWithPage(pageRequest.getOffset(), pageRequest.getPageSize())
                .stream()
                .map(SelectRoomListResponseDto::new).toList();
    }

    @Transactional
    public void updateRoom(User user, Long roomId, UpdateRequestDto requestDto) {
        Room room = roomRepository.findByIdAndManagerUserId(user.getId(), roomId).orElseThrow(
                () -> new CustomException(ErrorCode.ROOM_NOT_FOUND)
        );
        room.updateRoomName(requestDto.getRoomName());
    }

    @Transactional(readOnly = true)
    public Room findById(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(ErrorCode.ROOM_NOT_FOUND)
        );
    }
}
