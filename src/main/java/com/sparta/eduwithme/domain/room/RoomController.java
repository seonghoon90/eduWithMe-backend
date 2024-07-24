package com.sparta.eduwithme.domain.room;

import com.sparta.eduwithme.common.response.DataCommonResponse;
import com.sparta.eduwithme.common.response.StatusCommonResponse;
import com.sparta.eduwithme.domain.room.dto.*;
import com.sparta.eduwithme.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

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

    @GetMapping
    public ResponseEntity<DataCommonResponse<List<SelectRoomListResponseDto>>> getRoomListWithPage(
            @RequestParam(value = "page") int page)
    {
        List<SelectRoomListResponseDto> responseDtoList = roomService.getRoomListWithPage(page);
        DataCommonResponse<List<SelectRoomListResponseDto>> response = new DataCommonResponse<>(
                HttpStatus.OK.value(),
                "성공적으로 조회가 되었습니다.",
                responseDtoList);
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
}
