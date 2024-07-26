package com.sparta.eduwithme.domain.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.eduwithme.domain.user.dto.EmailCheckDto;
import com.sparta.eduwithme.domain.user.dto.EmailRequestDto;
import com.sparta.eduwithme.domain.user.dto.KeyValueResponseDto;
import com.sparta.eduwithme.domain.user.dto.SignupRequestDto;
import com.sparta.eduwithme.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SocialService socialService;
    private final MailSendService mailSendService;

    @PostMapping("/signup/request")
    public ResponseEntity<String> signupRequest(@Valid @RequestBody EmailRequestDto emailDto) {
        String authCode = userService.sendSignupVerificationEmail(emailDto.getEmail());
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

    @GetMapping("/key-value")
    public ResponseEntity<KeyValueResponseDto> loadKeyValue() {
        KeyValueResponseDto responseDto = new KeyValueResponseDto(socialService.getRedirectUri(), socialService.getAppKey());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoLogin(@RequestParam String code,
        HttpServletResponse response)
        throws JsonProcessingException {
        String token = socialService.kakaoLogin(code);

        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, token); // response header에 access token 넣기
        return ResponseEntity.status(HttpStatus.OK).body("카카오 로그인 하였습니다.");
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

}
