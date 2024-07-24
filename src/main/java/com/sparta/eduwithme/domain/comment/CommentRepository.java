package com.sparta.eduwithme.domain.comment;

import com.sparta.eduwithme.domain.comment.entity.Comment;
import com.sparta.eduwithme.domain.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByQuestion(Question question, Pageable pageable);
}
