package com.sparta.eduwithme.domain.room.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreatePrivateRoomRequestDto {
    @NotBlank(message = "방 제목은 필수로 입력해주세요.")
    private String roomName;
    @NotBlank(message = "private 룸은 비밀번호가 필수 입니다.")
    private String roomPassword;
}