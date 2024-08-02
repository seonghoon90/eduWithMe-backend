package com.sparta.eduwithme.domain.room;

import com.sparta.eduwithme.common.response.DataCommonResponse;
import com.sparta.eduwithme.common.response.StatusCommonResponse;
import com.sparta.eduwithme.domain.room.dto.*;
import com.sparta.eduwithme.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private static final Logger log = LoggerFactory.getLogger(RoomController.class);
    private final RoomService roomService;

    /**
     * [public room 생성 기능]
     * @param requestDto : roomTitle
     * @return : message, HttpStatusCode
     */
    @PostMapping("/public")
    public ResponseEntity<StatusCommonResponse> createPublicRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                 @RequestBody @Valid CreatePublicRoomRequestDto requestDto)
    {
        System.out.println("요청이옴");
        roomService.createPublicRoom(requestDto, userDetails.getUser());
        StatusCommonResponse response = new StatusCommonResponse(
                HttpStatus.CREATED.value(),
                "성공적으로 public 룸이 생성 되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/private")
    public ResponseEntity<StatusCommonResponse> createPrivateRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                  @RequestBody @Valid CreatePrivateRoomRequestDto requestDto)
    {
        roomService.createPrivateRoom(requestDto, userDetails.getUser());
        StatusCommonResponse response = new StatusCommonResponse(
                HttpStatus.CREATED.value(),
                "성공적으로 private 룸이 생성 되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 방 전체 조회
    @GetMapping
    public ResponseEntity<DataCommonResponse<List<RoomWithNickNameDto>>> getRoomListWithPage(
            @RequestParam(value = "page") int page)
    {
        List<RoomWithNickNameDto> responseDtoList = roomService.getRoomListWithPage(page);
        DataCommonResponse<List<RoomWithNickNameDto>> response = new DataCommonResponse<>(
                HttpStatus.OK.value(),
                "성공적으로 조회가 되었습니다.",
                responseDtoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 특정 유저가 속한 방 전체 조회
    @GetMapping("/{userId}")
    public ResponseEntity<DataCommonResponse<List<SelectAllUsersRoomResponseDto>>> selectAllUsersRoom(@PathVariable Long userId) {
        List<SelectAllUsersRoomResponseDto> responseDtoList = roomService.selectAllUsersRoom(userId);
        DataCommonResponse<List<SelectAllUsersRoomResponseDto>> response = new DataCommonResponse<>(200, "조회 성공", responseDtoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<StatusCommonResponse> updateRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                           @RequestBody UpdateRequestDto requestDto,
                                                           @PathVariable Long roomId)
    {
        roomService.updateRoom(userDetails.getUser(), roomId, requestDto);
        StatusCommonResponse response = new StatusCommonResponse(HttpStatus.OK.value(), "방 제목 변경 성공");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<StatusCommonResponse> deleteRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                           @PathVariable Long roomId)
    {
        roomService.deleteRoom(userDetails.getUser(), roomId);
        StatusCommonResponse response = new StatusCommonResponse(
                HttpStatus.NO_CONTENT.value(),
                "방 삭제가 완료 되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 방 입장 전 상세 조회(password 가 있는 방 인지 아닌지 체크)
    @PostMapping("/{roomId}")
    public ResponseEntity<DataCommonResponse<DetailRoomResponseDto>> selectDetailRoom(@PathVariable Long roomId) {
        DetailRoomResponseDto responseDto = roomService.selectDetailRoom(roomId);
        DataCommonResponse<DetailRoomResponseDto> response = new DataCommonResponse<>(HttpStatus.OK.value(),
                "방 상세 조회 성공",
                responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{roomId}/private")
    public ResponseEntity<DataCommonResponse<StudentResponseDto>> entryPrivateRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                   @RequestBody EntryPrivateRoomRequestDto requestDto,
                                                                                   @PathVariable Long roomId)
    {
        StudentResponseDto responseDto = roomService.entryPrivateRoom(roomId, requestDto.getRoomPassword(), userDetails.getUser());
        DataCommonResponse<StudentResponseDto> response = new DataCommonResponse<>(HttpStatus.OK.value(), "비공개 방 안에 입장이 되었습니다.", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{roomId}/public")
    public ResponseEntity<DataCommonResponse<StudentResponseDto>> entryPublicRoom(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                  @PathVariable Long roomId)
    {
        StudentResponseDto responseDto = roomService.entryPublicRoom(roomId, userDetails.getUser());
        DataCommonResponse<StudentResponseDto> response = new DataCommonResponse<>(HttpStatus.OK.value(), "공개 방 안에 입장이 되었습니다.", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{roomId}/users")
    public ResponseEntity<DataCommonResponse<List<RoomUserListResponseDto>>> selectRoomUsers(@PathVariable Long roomId) {
        List<RoomUserListResponseDto> responseDtoList = roomService.selectRoomUsers(roomId);
        DataCommonResponse<List<RoomUserListResponseDto>> response = new DataCommonResponse<>(200, "방 입장 인원 전체 조회 성공", responseDtoList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/one/{roomId}")
    public ResponseEntity<DataCommonResponse<SelectOneRoomResponseDto>> selectOneRoom(@PathVariable Long roomId) {
        SelectOneRoomResponseDto responseDto = roomService.selectOneRoom(roomId);
        DataCommonResponse<SelectOneRoomResponseDto> response = new DataCommonResponse<>(200, "방 단건 조회 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}