package com.sparta.eduwithme.domain.profile.dto;

import lombok.Data;

@Data
public class UpdateNicknameResponseDto {
    private String nickname; // 서버가 반환할 닉네임

    // 생성자
    public UpdateNicknameResponseDto(String nickname) {
        this.nickname = nickname;
    }
}
