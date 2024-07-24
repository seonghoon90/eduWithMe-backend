package com.sparta.eduwithme.domain.comment;

import com.sparta.eduwithme.domain.comment.dto.CommentRequestDto;
import com.sparta.eduwithme.domain.comment.dto.CommentResponseDto;
import com.sparta.eduwithme.domain.comment.entity.Comment;
import com.sparta.eduwithme.domain.question.QuestionService;
import com.sparta.eduwithme.domain.question.entity.Question;
import com.sparta.eduwithme.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<CommentResponseDto> getAllComments(Long questionId, int page, int pageSize) {
        Question question = questionService.findById(questionId);

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Comment> commentsPage = commentRepository.findAllByQuestion(question,pageable);
        return commentsPage.stream().map(CommentResponseDto::new).toList();
    }
}
