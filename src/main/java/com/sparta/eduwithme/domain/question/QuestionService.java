package com.sparta.eduwithme.domain.question;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.question.dto.*;
import com.sparta.eduwithme.domain.question.entity.Answer;
import com.sparta.eduwithme.domain.question.entity.Question;
import com.sparta.eduwithme.domain.room.RoomService;
import com.sparta.eduwithme.domain.room.entity.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
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

        Question question = new Question(
                room,
                requestDto.getTitle(),
                requestDto.getContent(),
                requestDto.getCategory(),
                requestDto.getDifficulty(),
                requestDto.getPoint(),
                answer
        );

        questionRepository.save(question);
        return new QuestionResponseDto(question);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponseDto> getAllQuestion(Long roomId, int page, int pageSize) {
        Room room = roomService.findById(roomId);

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
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
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
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

        question.updateQuestion(requestDto);

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

    @Transactional(readOnly = true)
    public Question findById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(
                () -> new CustomException(ErrorCode.QUESTION_NOT_FOUND)
        );
    }

}
