package com.sparta.eduwithme.domain.profile;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.profile.dto.UpdatePasswordRequestDto;
import com.sparta.eduwithme.domain.profile.dto.UserProfileDto;
import com.sparta.eduwithme.domain.profile.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileDto getUserProfile(Long userId) {
        User user = profileRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserProfileDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImage(user.getProfileImage())
                .userRank(user.getUserRank())
                .build();
    }

    public void updateUserProfile(Long userId, String email, String newNickname) {
        User user = profileRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        // 이메일이 일치하는지 확인
        if (!user.getEmail().equals(email)) {
            throw new CustomException(ErrorCode.EMAIL_MISMATCH);
        }

        user.updateNickname(newNickname);
        profileRepository.save(user);
    }

    public void updateUserPassword(Long userId, UpdatePasswordRequestDto request) {
        User user = profileRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!user.checkPassword(request.getCurrentPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        if (user.isPasswordRecentlyUsed(request.getNewPassword())) {
            throw new CustomException(ErrorCode.RECENT_PASSWORD_REUSE);
        }

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
        profileRepository.save(user);
    }
}
