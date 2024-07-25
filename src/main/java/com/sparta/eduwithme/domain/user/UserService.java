package com.sparta.eduwithme.domain.user;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.user.dto.SignupRequestDto;
import com.sparta.eduwithme.domain.user.entity.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

    public User findById(Long userId){
        return userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_UNIQUE));
    }
}

