package com.sparta.eduwithme.domain.comment;

import com.sparta.eduwithme.common.exception.CustomException;
import com.sparta.eduwithme.common.exception.ErrorCode;
import com.sparta.eduwithme.domain.comment.dto.CommentRequestDto;
import com.sparta.eduwithme.domain.comment.dto.CommentResponseDto;
import com.sparta.eduwithme.domain.comment.dto.CommentRoomDto;
import com.sparta.eduwithme.domain.comment.entity.Comment;
import com.sparta.eduwithme.domain.profile.ProfileRepository;
import com.sparta.eduwithme.domain.question.QuestionService;
import com.sparta.eduwithme.domain.question.entity.Question;
import com.sparta.eduwithme.domain.user.entity.User;
import com.vane.badwordfiltering.BadWordFiltering;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final QuestionService questionService;
    private final CommentRepository commentRepository;
    private final ProfileRepository profileRepository;
    private final BadWordFiltering badWordFiltering = new BadWordFiltering();

    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long questionId, User user) {
        Question question = questionService.findById(questionId);
        if (badWordFiltering.check(commentRequestDto.getComment())) {
            throw new CustomException(ErrorCode.PROFANITY_DETECTED);
        }
        Comment comment = commentRepository.save(new Comment(commentRequestDto, question, user));
        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public Page<CommentResponseDto> getAllComments(Long questionId, int page, int pageSize, Sort sort) {
        Question question = questionService.findById(questionId);
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Comment> commentsPage = commentRepository.findAllByQuestion(question, pageable);
        return commentsPage.map(CommentResponseDto::new);
    }

    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Long questionId, Long commentId, User user) {
        Question question = questionService.findById(questionId);
        Comment comment = findById(commentId);

        if (badWordFiltering.check(commentRequestDto.getComment())) {
            throw new CustomException(ErrorCode.PROFANITY_DETECTED);
        }

        if (!question.equals(comment.getQuestion())) {
            throw new CustomException(ErrorCode.COMMENT_QUESTION_MISMATCH);
        }

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_COMMENT_UPDATE);
        }

        comment.updateComment(commentRequestDto,user);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public void deleteComment(Long questionId, Long commentId, User user) {
        Question question = questionService.findById(questionId);
        Comment comment = findById(commentId);

        if (!question.equals(comment.getQuestion())) {
            throw new CustomException(ErrorCode.COMMENT_QUESTION_MISMATCH);
        }

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_COMMENT_DELETE);
        }

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new CustomException(ErrorCode.UNAUTHORIZED_COMMENT_DELETE)
        );
    }

    // 사용자 댓글 조회
    @Transactional(readOnly = true)
    public Page<CommentRoomDto> getCommentsWithRoomByUser(Long userId, Pageable pageable) {
        return commentRepository.findCommentsWithRoomAndQuestionByUserId(userId, pageable);
    }
}
