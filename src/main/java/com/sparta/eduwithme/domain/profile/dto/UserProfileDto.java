package com.sparta.eduwithme.domain.profile.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileDto {
    private String email;
    private String nickname;
    private String profileImage;
    private String userRank;
}
