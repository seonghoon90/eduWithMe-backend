package com.sparta.eduwithme.domain.question;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.question.dto.*;
import com.sparta.eduwithme.domain.question.entity.*;
import com.sparta.eduwithme.domain.room.RoomService;
import com.sparta.eduwithme.domain.room.entity.Room;
import com.sparta.eduwithme.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final LearningStatusRepository learningStatusRepository;
    private final RoomService roomService;

    @Transactional
    public QuestionResponseDto createQuestion(Long roomId, QuestionRequestDto requestDto) {
        Room room = roomService.findById(roomId);

        Answer answer = new Answer(
                requestDto.getAnswer().getFirst(),
                requestDto.getAnswer().getSecond(),
                requestDto.getAnswer().getThird(),
                requestDto.getAnswer().getFourth(),
                requestDto.getAnswer().getAnswered()
        );

        Long point = calculatePointByDifficulty(requestDto.getDifficulty());

        Question question = new Question(
                room,
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getCategory(),
                requestDto.getDifficulty(),
                point,
                answer
        );

        questionRepository.save(question);
        return new QuestionResponseDto(question);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponseDto> getAllQuestion(Long roomId, int page, int pageSize) {
        Room room = roomService.findById(roomId);

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Question> questionPage = questionRepository.findAllByRoom(room, pageable);
        return questionPage.stream()
                .map(QuestionResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuestionTitleDto> searchQuestionByTitle(Long roomId, String keyword, int page, int pageSize) {

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new CustomException(ErrorCode.KEYWORD_NOT_FOUND);
        }

        Room room = roomService.findById(roomId);

        String trimmedKeyword = keyword.trim();
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<Question> questionPage = questionRepository.findByRoomAndTitleContainingIgnoreCase(room, trimmedKeyword, pageable);

        return questionPage.getContent().stream()
                .map(QuestionTitleDto::new)
                .toList();
    }

    @Transactional
    public QuestionResponseDto updateQuestion(Long roomId, Long questionId, QuestionUpdateRequestDto requestDto) {
        Room room = roomService.findById(roomId);
        Question question = findById(questionId);

        if (!question.getRoom().getId().equals(room.getId())) {
            throw new CustomException(ErrorCode.QUESTION_ROOM_MISMATCH);
        }

        Long updatedPoint = calculatePointByDifficulty(requestDto.getDifficulty());

        question.updateQuestion(requestDto,updatedPoint);

        Answer answer = question.getAnswer();
        if (answer == null) {
            throw new CustomException(ErrorCode.ANSWER_NOT_FOUND);
        }
        answer.updateAnswer(requestDto.getAnswer());

        Question updatedQuestion = questionRepository.save(question);
        return new QuestionResponseDto(updatedQuestion);
    }

    @Transactional
    public void deleteQuestion(Long roomId, Long questionId) {
        Room room = roomService.findById(roomId);
        Question question = findById(questionId);

        if (!question.getRoom().getId().equals(room.getId())) {
            throw new CustomException(ErrorCode.QUESTION_ROOM_MISMATCH);
        }
        questionRepository.delete(question);
    }

    @Transactional(readOnly = true)
    public QuestionDetailDto getQuestionDetail(Long roomId, Long questionId) {
        Room room = roomService.findById(roomId);
        Question question = findById(questionId);

        if (!question.getRoom().getId().equals(room.getId())) {
            throw new CustomException(ErrorCode.QUESTION_ROOM_MISMATCH);
        }

        return new QuestionDetailDto(question);
    }

    @Transactional
    public AnswerResultDto submitAnswer(Long roomId, Long questionId, AnswerSubmissionDto submissionDto, User user) {
        Room room = roomService.findById(roomId);
        Question question = findById(questionId);

        if (!question.getRoom().getId().equals(room.getId())) {
            throw new CustomException(ErrorCode.QUESTION_ROOM_MISMATCH);
        }

        Answer answer = question.getAnswer();
        if (answer == null) {
            throw new CustomException(ErrorCode.ANSWER_NOT_FOUND);
        }

        boolean isCorrect = (submissionDto.getSelectedAnswer() == answer.getAnswered());
        Long earnedPoints = 0L;
        String message;

        Optional<LearningStatus> learningStatusOptional = learningStatusRepository.findByQuestionAndUser(question, user);

        if (isCorrect) {
            earnedPoints = question.getPoint();
            message = "정답입니다.";

            if (learningStatusOptional.isPresent()) { // 중복값 있는 경우 => DB에 저장되어있는 상태
                LearningStatus status = learningStatusOptional.get();
                if (status.getQuestionType() == QuestionType.WRONG) {
                    status.updateStatus(QuestionType.SOLVE);
                    learningStatusRepository.save(status);
                }
            } else { // 중복값이 없는 경우 => DB에 저장되어있지 않은 상태
                LearningStatus newStatus = new LearningStatus(question, user, QuestionType.SOLVE);
                learningStatusRepository.save(newStatus);
            }
        } else {
            message = "오답입니다.";

            if (!learningStatusOptional.isPresent()) {
                LearningStatus newStatus = new LearningStatus(question, user, QuestionType.WRONG);
                learningStatusRepository.save(newStatus);
            }
        }

        return new AnswerResultDto(isCorrect, earnedPoints, message);
    }

    @Transactional(readOnly = true)
    public Question findById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(
                () -> new CustomException(ErrorCode.QUESTION_NOT_FOUND)
        );
    }

    private Long calculatePointByDifficulty(Difficulty difficulty) {
        return switch (difficulty) {
            case LEVEL_ONE -> 10L;
            case LEVEL_TWO -> 20L;
            case LEVEL_THREE -> 30L;
            case LEVEL_FOUR -> 40L;
            case LEVEL_FIVE -> 50L;
            default -> throw new CustomException(ErrorCode.INVALID_NOT_DIFFICULTY);
        };
    }
}
