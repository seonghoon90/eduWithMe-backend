package com.sparta.eduwithme.domain.user;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.user.dto.SignupRequestDto;
import com.sparta.eduwithme.domain.user.entity.User;
import com.sparta.eduwithme.util.RedisUtil;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailSendService mailSendService;
    private final RedisUtil redisUtil;

    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String email = requestDto.getEmail();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickName = requestDto.getNickName();

        // 중복 체크 있으면 => 이미 등록된 회원
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new CustomException(ErrorCode.USER_NOT_UNIQUE);
        }

        // 회원가입한 user DB에 저장
        userRepository.save(new User(email, password, nickName));
    }

    // 주어진 이메일이 등록된 이메일인지 확인하는 메서드
    public boolean isRegisteredEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // 임시 비밀번호 발급을 요청하는 메서드
    public void requestTempPassword(String email) {
        // 이메일이 등록되지 않았다면 예외를 던집니다.
        if (!isRegisteredEmail(email)) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        // 임시 비밀번호 발급을 위한 이메일을 보냅니다.
        mailSendService.sendTempPasswordEmail(email);
    }

    public void resetPasswordWithTempPassword(String email, String authCode) {
        if (!mailSendService.CheckAuthNum(email, authCode)) {
            throw new CustomException(ErrorCode.INVALID_VERIFICATION_CODE);
        }

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String tempPassword = generateTempPassword();
        user.updatePassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        mailSendService.sendTempPassword(email, tempPassword);

        redisUtil.deleteData(authCode);
    }

    private String generateTempPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_UNIQUE));
    }
}

