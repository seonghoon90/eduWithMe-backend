package com.sparta.eduwithme.domain.room;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.room.dto.*;
import com.sparta.eduwithme.domain.room.entity.Room;
import com.sparta.eduwithme.domain.room.entity.Student;
import com.sparta.eduwithme.domain.room.repository.RoomRepository;
import com.sparta.eduwithme.domain.room.repository.StudentRepository;
import com.sparta.eduwithme.domain.user.entity.User;
import com.vane.badwordfiltering.BadWordFiltering;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;
    private final BadWordFiltering badWordFiltering = new BadWordFiltering();

    private static final int ROOM_CREATE_LIMIT = 2;

    // public room
    public void createPublicRoom(CreatePublicRoomRequestDto requestDto, User user) {
        isDuplicationRoomName(requestDto.getRoomName());
        countManagerRooms(user.getId());

        if (badWordFiltering.check(requestDto.getRoomName())) {
            throw new CustomException(ErrorCode.PROFANITY_DETECTED);
        }

        Room room = roomRepository.save(Room.builder()
                .roomName(requestDto.getRoomName())
                .managerUserId(user.getId()).build());

        Student student = Student.builder().user(user).room(room).build();
        studentRepository.save(student);
    }

    // private room
    public void createPrivateRoom(CreatePrivateRoomRequestDto requestDto, User user) {
        isDuplicationRoomName(requestDto.getRoomName());
        countManagerRooms(user.getId());

        if (badWordFiltering.check(requestDto.getRoomName())) {
            throw new CustomException(ErrorCode.PROFANITY_DETECTED);
        }

        Room room = roomRepository.save(Room.builder()
                .roomName(requestDto.getRoomName())
                .roomPassword(requestDto.getRoomPassword())
                .managerUserId(user.getId()).build());

        Student student = Student.builder().user(user).room(room).build();
        studentRepository.save(student);
    }

    // 똑같은 이름에 대한 방 확인
    private void isDuplicationRoomName(String roomName) {
        if(roomRepository.findByRoomName(roomName).isPresent()) {
            throw new CustomException(ErrorCode.SAME_NEW_ROOM_NAME);
        }
    }

    // 방 만든 유저의 방 갯수 제한
    private void countManagerRooms(Long userId) {
        Long roomCount = roomRepository.countByManagerUserId(userId);
        if(roomCount >= ROOM_CREATE_LIMIT) {
            throw new CustomException(ErrorCode.CAN_NOT_MADE_ROOM);
        }
    }

    public PagedRoomResponse getRoomListWithPage(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<RoomWithNickNameDto> roomPage = roomRepository.getRoomListWithPage(pageRequest);

        return new PagedRoomResponse(
            roomPage.getContent(),
            roomPage.getTotalPages(),
            roomPage.getTotalElements()
        );
    }

    public List<SelectAllUsersRoomResponseDto> selectAllUsersRoom(Long userId) {
        List<Student> studentList = studentRepository.findStudentsWithRoomByUserId(userId);
        return studentList.stream().map(SelectAllUsersRoomResponseDto::new).toList();
    }

    @Transactional
    public void updateRoom(User user, Long roomId, UpdateRequestDto requestDto) {
        Room room = findByIdAndManagerUserId(user, roomId);

        if (badWordFiltering.check(requestDto.getRoomName())) {
            throw new CustomException(ErrorCode.PROFANITY_DETECTED);
        }

        room.updateRoomName(requestDto.getRoomName());
    }

    public void deleteRoom(User user, Long roomId) {
        Room room = findByIdAndManagerUserId(user, roomId);
        roomRepository.delete(room);
    }

    private Room findByIdAndManagerUserId(User user, Long roomId) {
        boolean isRoomExist = roomRepository.findById(roomId).isPresent();
        if(!isRoomExist) {
            throw new CustomException(ErrorCode.ROOM_NOT_FOUND);
        }
        return roomRepository.findByIdAndManagerUserId(roomId, user.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.ROOM_NOT_OWNER)
        );
    }

    // true = 비밀번호 있는 방
    // false = 비밀번호 없는 방
    @Transactional
    public DetailRoomResponseDto selectDetailRoom(Long roomId) {
        Room room = findById(roomId);
        if(Objects.isNull(room.getRoomPassword())) {
            return new DetailRoomResponseDto(room, false);
        }
        return new DetailRoomResponseDto(room, true);
    }

    @Transactional
    public StudentResponseDto entryPrivateRoom(Long roomId, String roomPassword, User user) {
        Room room = findById(roomId);
        if((Objects.isNull(room.getRoomPassword()))) {
            throw new CustomException(ErrorCode.TRYING_TO_ENTER_INVALID_ROOM);
        }
        boolean isPwdVerification = roomRepository.findByIdAndRoomPassword(roomId, roomPassword).isPresent();
        if(!isPwdVerification) {
            throw new CustomException(ErrorCode.ROOM_INCORRECT_PASSWORD);
        }
        Optional<Student> studentOptional = studentRepository.findByRoomIdAndUserIdWithJoin(room.getId(), user.getId());

        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return new StudentResponseDto(student);
        }
        Student student = studentRepository.save(Student.builder().user(user).room(room).build());
        return new StudentResponseDto(student);
    }

    @Transactional
    public StudentResponseDto entryPublicRoom(Long roomId, User user) {
        Room room = findById(roomId);
        if(!(Objects.isNull(room.getRoomPassword()))) {
            throw new CustomException(ErrorCode.TRYING_TO_ENTER_INVALID_ROOM);
        }
        Optional<Student> studentOptional = studentRepository.findByRoomIdAndUserIdWithJoin(room.getId(), user.getId());
        if(studentOptional.isPresent()) {
            Student student = studentOptional.get();
            return new StudentResponseDto(student);
        }
        Student student = studentRepository.save(Student.builder().user(user).room(room).build());
        return new StudentResponseDto(student);
    }

    public List<RoomUserListResponseDto> selectRoomUsers(Long roomId) {
        List<Student> student = studentRepository.findByRoomIdWithUser(roomId);
        return student.stream().map(RoomUserListResponseDto::new).toList();
    }

    public SelectOneRoomResponseDto selectOneRoom(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(ErrorCode.ROOM_NOT_FOUND)
        );
        return new SelectOneRoomResponseDto(room);
    }

    @Transactional(readOnly = true)
    public Room findById(Long roomId) {
        return roomRepository.findById(roomId).orElseThrow(
                () -> new CustomException(ErrorCode.ROOM_NOT_FOUND)
        );
    }

}