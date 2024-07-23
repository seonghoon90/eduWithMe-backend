package com.sparta.eduwithme.domain.profile.dto;

import lombok.Getter;

@Getter
public class UpdatePasswordResponseDto {
    private String message;

    public UpdatePasswordResponseDto(String message) {
        this.message = message;
    }
}
