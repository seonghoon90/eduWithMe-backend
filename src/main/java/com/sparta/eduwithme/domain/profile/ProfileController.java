package com.sparta.eduwithme.domain.profile;

import com.sparta.eduwithme.domain.profile.dto.UpdateNicknameRequestDto;
import com.sparta.eduwithme.domain.profile.dto.UpdatePasswordRequestDto;
import com.sparta.eduwithme.domain.profile.dto.UserProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        profileService.updateUserProfile(userId, request.getEmail(), request.getNickName());

        return new ResponseEntity<>("프로필 수정이 완료되었습니다.", HttpStatus.OK);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<String> updatePassword(@PathVariable Long userId,
                                                 @RequestBody UpdatePasswordRequestDto request) {
        profileService.updateUserPassword(userId, request);
        return new ResponseEntity<>("비밀번호 수정이 완료되었습니다.", HttpStatus.OK);
    }
}