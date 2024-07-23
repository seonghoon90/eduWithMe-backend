package com.sparta.eduwithme.domain.profile.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateNicknameRequestDto {
    private String email;
    private String nickname; // 사용자로부터 받을 닉네임

    // 인수 생성자
    public UpdateNicknameRequestDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }
}
