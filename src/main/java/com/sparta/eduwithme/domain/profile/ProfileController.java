package com.sparta.eduwithme.domain.profile;

import com.sparta.eduwithme.domain.profile.dto.UpdateNicknameRequestDto;
import com.sparta.eduwithme.domain.profile.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{userId}")
    public UserProfileDto getUserProfile(@PathVariable Long userId) {
        return profileService.getUserProfile(userId);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateProfile(@PathVariable Long userId,
                                                @RequestBody UpdateNicknameRequestDto request) {
        profileService.updateUserProfile(userId, request.getEmail(), request.getNickname());

        return ResponseEntity.ok("프로필 수정이 완료되었습니다.");
    }
}