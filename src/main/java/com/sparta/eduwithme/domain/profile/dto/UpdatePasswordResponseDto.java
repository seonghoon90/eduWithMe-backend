package com.sparta.eduwithme.domain.profile.dto;

import lombok.Data;

@Data
public class UpdatePasswordResponseDto {
    private String message;

    public UpdatePasswordResponseDto(String message) {
        this.message = message;
    }
}
