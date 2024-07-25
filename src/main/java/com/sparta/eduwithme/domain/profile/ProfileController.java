package com.sparta.eduwithme.domain.profile;

import com.sparta.eduwithme.common.response.DataCommonResponse;
import com.sparta.eduwithme.common.response.StatusCommonResponse;
import com.sparta.eduwithme.domain.profile.dto.*;
import com.sparta.eduwithme.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    // 프로필 사진 업로드
    @PostMapping("/photo")
    public ResponseEntity<UploadPhotoResponseDto> uploadProfilePhoto(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                     @RequestParam("file") MultipartFile file) {
        Long userId = userDetails.getUser().getId();
        try {
            String fileUrl = profileService.uploadProfilePhoto(userId, file);
            UploadPhotoResponseDto response = new UploadPhotoResponseDto(
                    HttpStatus.OK.value(),
                    "프로필 사진이 성공적으로 업로드되었습니다.",
                    fileUrl
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            UploadPhotoResponseDto response = new UploadPhotoResponseDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "프로필 사진 업로드에 실패했습니다.",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 해결한 문제 조회
    @GetMapping("/solve")
    public ResponseEntity<DataCommonResponse<List<QuestionDto>>> getSolvedQuestions(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        List<QuestionDto> solvedQuestions = profileService.getSolvedQuestions(userId);
        DataCommonResponse<List<QuestionDto>> response = new DataCommonResponse<>(
                HttpStatus.OK.value(),
                "해결한 문제 조회 성공",
                solvedQuestions
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}