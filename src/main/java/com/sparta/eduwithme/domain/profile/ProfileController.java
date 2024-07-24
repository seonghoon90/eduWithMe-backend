package com.sparta.eduwithme.domain.profile;

import com.sparta.eduwithme.common.response.DataCommonResponse;
import com.sparta.eduwithme.common.response.StatusCommonResponse;
import com.sparta.eduwithme.domain.profile.dto.*;
import com.sparta.eduwithme.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    // 프로필 조회
    @GetMapping
    public ResponseEntity<DataCommonResponse<UserProfileDto>> getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        UserProfileDto userProfile = profileService.getUserProfile(userId);
        DataCommonResponse<UserProfileDto> response = new DataCommonResponse<>(
                HttpStatus.OK.value(),
                "프로필 조회 성공",
                userProfile
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 닉네임 수정
    @PutMapping
    public ResponseEntity<StatusCommonResponse> updateNickname(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                               @RequestBody UpdateNicknameRequestDto requestDto) {
        Long userId = userDetails.getUser().getId();
        profileService.updateUserProfile(userId, requestDto.getEmail(), requestDto.getNickName());
        StatusCommonResponse response = new StatusCommonResponse(
                HttpStatus.OK.value(),
                "닉네임이 성공적으로 변경되었습니다."
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 비밀번호 수정
    @PutMapping("/password")
    public ResponseEntity<StatusCommonResponse> updatePassword(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                               @RequestBody @Valid UpdatePasswordRequestDto requestDto) {
        Long userId = userDetails.getUser().getId();
        profileService.updateUserPassword(userId, requestDto);
        StatusCommonResponse response = new StatusCommonResponse(
                HttpStatus.OK.value(),
                "비밀번호가 성공적으로 변경되었습니다."
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}