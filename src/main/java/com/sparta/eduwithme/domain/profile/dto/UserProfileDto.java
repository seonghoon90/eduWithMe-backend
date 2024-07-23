package com.sparta.eduwithme.domain.profile.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileDto {
    private String email;
    private String nickName;
    private String photoUrl;
    private String ranking;
}
