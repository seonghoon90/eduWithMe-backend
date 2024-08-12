package com.sparta.eduwithme.domain.profile;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.profile.dto.QuestionDto;
import com.sparta.eduwithme.domain.profile.dto.UpdatePasswordRequestDto;
import com.sparta.eduwithme.domain.profile.dto.UserProfileDto;
import com.sparta.eduwithme.domain.question.repository.LearningStatusRepository;
import com.sparta.eduwithme.domain.question.entity.QuestionType;
import com.sparta.eduwithme.domain.user.UserRepository;
import com.sparta.eduwithme.domain.user.entity.User;
import com.vane.badwordfiltering.BadWordFiltering;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final LearningStatusRepository learningStatusRepository;
    private final BadWordFiltering badWordFiltering = new BadWordFiltering();
    private final UserRepository userRepository;

    private String uploadDir;

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");

    public UserProfileDto getUserProfile(Long userId) {
        User user = profileRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        // 문제를 푼 포인트 총합 가져오기
        Long totalPoints = learningStatusRepository.findTotalPointsByUserIdAndQuestionType(userId, QuestionType.SOLVE);
        if (totalPoints == null) {
            totalPoints = 0L;
        }

        // 랭킹 계산
        String ranking = calculateRanking(totalPoints);

        return UserProfileDto.builder()
                .email(user.getEmail())
                .nickName(user.getNickName())
                .photoUrl(user.getPhotoUrl())
                .ranking(ranking)
                .points(totalPoints)
                .build();
    }

    private String calculateRanking(Long totalPoints) {
        if (totalPoints == null) {
            return "F";
        }
        if (totalPoints <= 50) {
            return "F";
        } else if (totalPoints <= 100) {
            return "D";
        } else if (totalPoints <= 150) {
            return "C";
        } else if (totalPoints <= 200) {
            return "B";
        } else {
            return "A";
        }
    }

    @Transactional
    public void updateUserProfile(Long userId, String email, String newNickname) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!user.getEmail().equals(email)) {
            throw new CustomException(ErrorCode.EMAIL_MISMATCH);
        }

        if (!user.getNickName().equals(newNickname) && !isNicknameAvailable(newNickname)) {
            throw new CustomException(ErrorCode.NICKNAME_ALREADY_EXISTS);
        }

        if (badWordFiltering.check(newNickname)) {
            throw new CustomException(ErrorCode.PROFANITY_DETECTED);
        }

        user.updateNickname(newNickname);
        userRepository.save(user);
    }

    private boolean isNicknameAvailable(String nickname) {
        return !userRepository.findByNickName(nickname).isPresent();
    }

    public void updateUserPassword(Long userId, UpdatePasswordRequestDto request) {
        User user = profileRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        // 비밀번호 형식 검증
        if (!PASSWORD_PATTERN.matcher(request.getNewPassword()).matches()) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        // 현재 비밀번호 검증
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.WRONG_PASSWORD);
        }

        user.updatePassword(passwordEncoder.encode(request.getNewPassword()));
        profileRepository.save(user);
    }

    public Page<QuestionDto> getSolvedQuestions(Long userId, Pageable pageable) {
        return learningStatusRepository.findQuestionsWithRoomByUserAndQuestionType(userId, QuestionType.SOLVE, pageable);
    }

    public Page<QuestionDto> getWrongQuestions(Long userId, Pageable pageable) {
        return learningStatusRepository.findQuestionsWithRoomByUserAndQuestionType(userId, QuestionType.WRONG, pageable);
    }
}