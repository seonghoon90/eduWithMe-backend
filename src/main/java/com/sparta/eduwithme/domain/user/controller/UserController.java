package com.sparta.eduwithme.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.eduwithme.common.response.StatusCommonResponse;
import com.sparta.eduwithme.domain.user.service.SocialService;
import com.sparta.eduwithme.domain.user.service.UserService;
import com.sparta.eduwithme.domain.user.dto.*;
import com.sparta.eduwithme.domain.user.entity.User;
import com.sparta.eduwithme.security.UserDetailsImpl;
import com.sparta.eduwithme.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SocialService socialService;
    private final JwtUtil jwtUtil;

    @Value("${frontend.kakao.domain}")
    private String kakaoDomain;

    @PostMapping("/signup/request")
    public ResponseEntity<String> signupRequest(@Valid @RequestBody EmailRequestDto emailDto) {
        userService.sendSignupVerificationEmail(emailDto.getEmail());
        return ResponseEntity.ok("인증 코드가 이메일로 전송되었습니다.");
    }

    @PostMapping("/signup/verify")
    public ResponseEntity<String> verifySignup(@Valid @RequestBody EmailCheckDto emailCheckDto) {
        userService.verifySignupEmail(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
        return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
    }

    /**
     * 회원가입 API
     *
     * @param requestDto : 회원가입 정보가 담긴 dto
     * @return
     */
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(@RequestBody RefreshTokenRequestDto request, HttpServletResponse res) {
        userService.accessTokenReissue(request.getRefreshToken(), res);
        return new ResponseEntity<>("토큰 재발급 성공", HttpStatus.OK);
    }

    @GetMapping("/key-value")
    public ResponseEntity<KeyValueResponseDto> loadKeyValue() {
        KeyValueResponseDto responseDto = new KeyValueResponseDto(socialService.getRedirectUri(), socialService.getAppKey());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/kakao/callback")
    public void kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException, UnsupportedEncodingException {
        User user = socialService.kakaoLogin(code);
        String token = jwtUtil.createAccessToken(user);

        String redirectUrl = UriComponentsBuilder.fromUriString(kakaoDomain)
            .queryParam("token", token)
            .queryParam("userId", user.getId())
            .queryParam("nickName", URLEncoder.encode(user.getNickName(), StandardCharsets.UTF_8.toString()))
            .build().toUriString();

        response.setHeader("Location", redirectUrl);
        response.setStatus(HttpServletResponse.SC_FOUND);
    }

    // 임시 비밀번호 발급 요청을 처리하는 엔드포인트
    @PostMapping("/temp-password-request")
    public ResponseEntity<String> requestTempPassword(@RequestBody @Valid EmailRequestDto emailDto) {
        if (!userService.isRegisteredEmail(emailDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("등록되지 않은 이메일입니다.");
        }
        // 임시 비밀번호 발급을 요청합니다.
        userService.requestTempPassword(emailDto.getEmail());
        return ResponseEntity.ok("임시 비밀번호 발급을 위한 인증 코드가 이메일로 전송되었습니다.");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody EmailCheckDto emailCheckDto) {
        userService.resetPasswordWithTempPassword(emailCheckDto.getEmail(), emailCheckDto.getAuthNum());
        return ResponseEntity.ok("임시 비밀번호가 이메일로 전송되었습니다.");
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<?> checkNicknameAvailability(@RequestParam String nickname) {
        boolean isAvailable = userService.isNicknameAvailable(nickname);
        Map<String, Boolean> response = new HashMap<>();
        response.put("available", isAvailable);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            userService.deleteUser(userDetails.getUser().getId());
            return ResponseEntity.ok().body(new StatusCommonResponse(200, "회원탈퇴가 완료되었습니다."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new StatusCommonResponse(500, "회원탈퇴 처리 중 오류가 발생했습니다."));
        }
    }

}
