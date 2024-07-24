package com.sparta.eduwithme.domain.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UploadPhotoResponseDto {
    private int statusCode;
    private String message;
    private String photoUrl;
}
