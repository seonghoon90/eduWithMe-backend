package com.sparta.eduwithme.domain.room.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreatePublicRoomRequestDto {
    @NotBlank(message = "방 제목은 필수로 입력해주세요.")
    private String roomName;
}
