package com.sparta.eduwithme.domain.room;

import com.sparta.eduwithme.common.response.StatusCommonResponse;
import com.sparta.eduwithme.domain.room.dto.CreateRoomRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    /**
     * [방 생성 기능] %=> 로그인 및 토큰 기능 구현 되면 그때 Details 뽑을 예정
     * @param requestDto : (방 제목, 방 패스워드)
     * @return : message, HttpStatusCode
     */
    @PostMapping
    public ResponseEntity<StatusCommonResponse> createRoom(
            @RequestBody CreateRoomRequestDto requestDto)
    {
        Long managerUserId = 1L;
        roomService.createRoom(requestDto, managerUserId);
        StatusCommonResponse response = new StatusCommonResponse(
                HttpStatus.CREATED.value(),
                "성공적으로 방 생성이 되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
