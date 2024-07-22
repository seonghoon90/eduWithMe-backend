package com.sparta.eduwithme.domain.profile;

import com.sparta.eduwithme.domain.profile.dto.UserProfileDto;
import com.sparta.eduwithme.domain.profile.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileRespository profileRespository;

    public UserProfileDto getUserProfile(Long userId) {
        User user = profileRespository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("User not found with id: " + userId));

        return UserProfileDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .userRank(user.getUserRank())
                .build();
    }
}