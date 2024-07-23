package com.sparta.eduwithme.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.eduwithme.common.response.StatusCommonResponse;
import com.sparta.eduwithme.domain.user.UserRepository;
import com.sparta.eduwithme.domain.user.dto.LoginRequestDto;
import com.sparta.eduwithme.domain.user.entity.User;
import com.sparta.eduwithme.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "JwtAuthenticationFilter")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        setFilterProcessesUrl("/users/login");
    }

    // 내부 로직에서 호출 ( 인증 )
    // 아이디와 비밀번호를 들고 DB 에서 조회해서 검증을 하고 돌아옴.
    public Authentication attemptAuthentication(
        HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        if (!Objects.equals(request.getMethod(), "POST")) {
            throw new AuthenticationServiceException("잘못된 HTTP 요청 입니다.");
        }

        try {
            LoginRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDto.class);

            return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                    requestDto.getEmail(),
                    requestDto.getPassword(),
                    null
                )
            );
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    // 내부에서 호출하는데 new UsernamePasswordAuthenticationToken(
    //                    requestDto.getUsername(),
    //                    requestDto.getPassword(),
    // 이 값이 존재하는지 if문으로 되어있고 값이 있으면 successfulAuthentication
    @Override
    protected void successfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult) throws IOException, ServletException
    {
        User user = ((UserDetailsImpl) authResult.getPrincipal()).getUser();
        String accessToken = jwtUtil.createAccessToken(user);
//        String refreshToken = jwtUtil.createRefreshToken(user);

//        updateRefreshToken(user, refreshToken);

        StatusCommonResponse responseDto = new StatusCommonResponse(HttpStatus.OK.value(), "로그인이 성공적으로 되었습니다.");

        response.addHeader(JwtUtil.ACCESS_TOKEN_HEADER, accessToken);
//        response.addHeader(JwtUtil.REFRESH_TOKEN_HEADER, refreshToken);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseDto));
    }

//    private void updateRefreshToken(User user, String refreshToken) {
//        String originalRefreshToken = jwtUtil.refreshTokenSubstring(refreshToken);
//        user.updateRefreshToken(originalRefreshToken);
//        userRepository.save(user);
//        log.info("{}", "[로그인 시점][DB] refreshToken 업데이트 성공");
//    }

    @Override
    protected void unsuccessfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException failed) throws ServletException, IOException {
        response.setStatus(401);
    }
}
