package com.sparta.eduwithme.domain.profile;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.profile.dto.QuestionDto;
import com.sparta.eduwithme.domain.profile.dto.UpdatePasswordRequestDto;
import com.sparta.eduwithme.domain.profile.dto.UserProfileDto;
import com.sparta.eduwithme.domain.question.LearningStatusRepository;
import com.sparta.eduwithme.domain.question.QuestionRepository;
import com.sparta.eduwithme.domain.question.entity.LearningStatus;
import com.sparta.eduwithme.domain.question.entity.Question;
import com.sparta.eduwithme.domain.question.entity.QuestionType;
import com.sparta.eduwithme.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final LearningStatusRepository learningStatusRepository;

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
        if(totalPoints == null) {
            return "F";
        }
        if(totalPoints <= 50) {
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

    public void updateUserProfile(Long userId, String email, String newNickname) {
        User user = profileRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        // 이메일이 일치하는지 확인
        if (!user.getEmail().equals(email)) {
            throw new CustomException(ErrorCode.EMAIL_MISMATCH);
        }

        user.updateNickname(newNickname);
        profileRepository.save(user);
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

    // 프로필 사진 업로드
    public String uploadProfilePhoto(Long userId, MultipartFile file) {
        String directoryPath = uploadDir + "images/" + userId + "/";
        String filename = storeFile(directoryPath, file);
        String fileUrl = "/images/" + userId + "/" + filename;

        // 사용자 프로필 사진 URL 업데이트
        User user = profileRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updatePhotoUrl(fileUrl);
        profileRepository.save(user);

        return fileUrl;
    }

    private String storeFile(String directoryPath, MultipartFile file) {
        Path uploadPath = Paths.get(directoryPath);

        // 디렉토리 생성
        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_DIRECTORY_CREATION_FAILED);
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path destinationFile = uploadPath.resolve(Paths.get(filename)).normalize().toAbsolutePath();

        try {
            Files.copy(file.getInputStream(), destinationFile);
        } catch (IOException e) {
            throw new CustomException(ErrorCode.FILE_STORAGE_FAILED);
        }

        return filename;
    }

    public Page<QuestionDto> getSolvedQuestions(Long userId, Pageable pageable) {
        User user = profileRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        Page<LearningStatus> learningStatuses = learningStatusRepository.findByUserAndQuestionType(user, QuestionType.SOLVE, pageable);

        List<QuestionDto> solvedQuestions = learningStatuses.stream()
                .map(ls -> {
                    Question question = ls.getQuestion();
                    return new QuestionDto(
                            question.getId(),
                            question.getCategory().getCategoryName(),
                            question.getTitle(),
                            question.getDifficulty().toString(),
                            question.getCreatedAt().toString()
                    );
                })
                .collect(Collectors.toList());

        return new PageImpl<>(solvedQuestions, pageable, learningStatuses.getTotalElements());
    }

    public Page<QuestionDto> getWrongQuestions(Long userId, Pageable pageable) {
        User user = profileRepository.findById(userId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND));

        Page<LearningStatus> learningStatuses = learningStatusRepository.findByUserAndQuestionType(user, QuestionType.WRONG, pageable);

        List<QuestionDto> wrongQuestions = learningStatuses.stream()
                .map(ls -> {
                    Question question = ls.getQuestion();
                    return new QuestionDto(
                            question.getId(),
                            question.getCategory().getCategoryName(),
                            question.getTitle(),
                            question.getDifficulty().toString(),
                            question.getCreatedAt().toString()
                    );
                })
                .collect(Collectors.toList());

        return new PageImpl<>(wrongQuestions, pageable, learningStatuses.getTotalElements());
    }
}