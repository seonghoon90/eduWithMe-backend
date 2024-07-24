package com.sparta.eduwithme.domain.user;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    @GetMapping("/kakao/callback")
    public ResponseEntity<String> kakaoLogin(@RequestParam String code,
        HttpServletResponse response)
        throws JsonProcessingException {
        String token = socialService.kakaoLogin(code);

        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, token); // response header에 access token 넣기
        return ResponseEntity.status(HttpStatus.OK).body("카카오 로그인 하였습니다.");
    }
}
