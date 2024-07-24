package com.sparta.eduwithme.domain.comment;

import com.sparta.eduwithme.domain.comment.dto.CommentRequestDto;
import com.sparta.eduwithme.domain.comment.dto.CommentResponseDto;
import com.sparta.eduwithme.domain.comment.entity.Comment;
import com.sparta.eduwithme.domain.question.QuestionService;
import com.sparta.eduwithme.domain.question.entity.Question;
import com.sparta.eduwithme.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final QuestionService questionService;
    private final CommentRepository commentRepository;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long questionId, User user) {
        Question question = questionService.findById(questionId);
        Comment comment = commentRepository.save(new Comment(commentRequestDto, question, user));
        return new CommentResponseDto(comment);
    }
}
