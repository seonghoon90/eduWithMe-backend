package com.sparta.eduwithme.domain.profile.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateNicknameRequestDto {
    private String email;
    private String nickName; // 사용자로부터 받을 닉네임

    // 인수 생성자
    public UpdateNicknameRequestDto(String email, String nickName) {
        this.email = email;
        this.nickName = nickName;
    }
}
