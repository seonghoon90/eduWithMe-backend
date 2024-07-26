package com.sparta.eduwithme.domain.user.dto;

import lombok.Getter;

@Getter
public class KeyValueResponseDto {
    private String redirectUri;
    private String appKey;

    public KeyValueResponseDto(String redirectUri, String appKey) {
        this.redirectUri = redirectUri;
        this.appKey = appKey;
    }
}
