package com.sparta.eduwithme.domain.question;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.question.dto.AnswerRequestDTO;
import com.sparta.eduwithme.domain.question.dto.QuestionRequestDTO;
import com.sparta.eduwithme.domain.question.dto.QuestionResponseDTO;
import com.sparta.eduwithme.domain.question.dto.QuestionTitleDTO;
import com.sparta.eduwithme.domain.question.entity.Answer;
import com.sparta.eduwithme.domain.question.entity.Question;
import com.sparta.eduwithme.domain.room.repository.RoomRepository;
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
    private final RoomRepository roomRepository;

    @Transactional
    public QuestionResponseDTO createQuestion(Long roomId, QuestionRequestDTO requestDTO) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

        Question question = new Question(room, requestDTO);

        for (AnswerRequestDTO answerRequestDTO : requestDTO.getAnswerList()) {
            Answer answer = new Answer(answerRequestDTO);

            question.addAnswer(answer);
        }
        questionRepository.save(question);
        return new QuestionResponseDTO(question);

    }

    @Transactional(readOnly = true)
    public List<QuestionResponseDTO> getAllQuestion(Long roomId, int page, int pageSize) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Question> questionPage = questionRepository.findAllByRoom(room, pageable);
        return questionPage.stream().map(QuestionResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public List<QuestionTitleDTO> searchQuestionByTitle(Long roomId, String keyword, int page, int pageSize) {

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new CustomException(ErrorCode.KEYWORD_NOT_FOUND);
        }

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ErrorCode.ROOM_NOT_FOUND));

        String trimmedKeyword = keyword.trim();
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Question> questionPage = questionRepository.findByRoomAndTitleContainingIgnoreCase(room, trimmedKeyword, pageable);

        return questionPage.getContent().stream()
                .map(QuestionTitleDTO::new)
                .toList();
    }
}
