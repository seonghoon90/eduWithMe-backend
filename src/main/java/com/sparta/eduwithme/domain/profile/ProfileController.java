package com.sparta.eduwithme.domain.profile;

import com.sparta.eduwithme.common.response.DataCommonResponse;
import com.sparta.eduwithme.common.response.StatusCommonResponse;
import com.sparta.eduwithme.domain.comment.CommentService;
import com.sparta.eduwithme.domain.comment.dto.CommentResponseDto;
import com.sparta.eduwithme.domain.comment.entity.Comment;
import com.sparta.eduwithme.domain.profile.dto.*;
import com.sparta.eduwithme.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    private final CommentService commentService;

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
    public ResponseEntity<DataCommonResponse<Page<QuestionDto>>> getSolvedQuestions(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                    @RequestParam(defaultValue = "0") int page,
                                                                                    @RequestParam(defaultValue = "5") int size) {
        Long userId = userDetails.getUser().getId();
        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionDto> solvedQuestions = profileService.getSolvedQuestions(userId, pageable);
        DataCommonResponse<Page<QuestionDto>> response = new DataCommonResponse<>(
                HttpStatus.OK.value(),
                "해결한 문제 조회 성공",
                solvedQuestions
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 오답 문제 조회
    @GetMapping("/wrong")
    public ResponseEntity<DataCommonResponse<Page<QuestionDto>>> getWrongQuestions(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                                                   @RequestParam(defaultValue = "0") int page,
                                                                                   @RequestParam(defaultValue = "5") int size) {
        Long userId = userDetails.getUser().getId();
        Pageable pageable = PageRequest.of(page, size);
        Page<QuestionDto> wrongQuestions = profileService.getWrongQuestions(userId, pageable);
        DataCommonResponse<Page<QuestionDto>> response = new DataCommonResponse<>(
                HttpStatus.OK.value(),
                "오답 문제 조회 성공",
                wrongQuestions
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 작성한 댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<DataCommonResponse<List<CommentResponseDto>>> getUserComments(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {

        Long userId = userDetails.getUser().getId();
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> userCommentsPage = commentService.getCommentsByUser(userId, pageable);

        List<CommentResponseDto> commentsDtoList = userCommentsPage.stream()
                .map(CommentResponseDto::new)
                .toList();

        DataCommonResponse<List<CommentResponseDto>> response = new DataCommonResponse<>(
                HttpStatus.OK.value(),
                "댓글 조회 성공.",
                commentsDtoList
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}