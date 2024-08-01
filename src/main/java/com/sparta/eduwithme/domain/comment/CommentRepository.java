package com.sparta.eduwithme.domain.comment;

import com.sparta.eduwithme.domain.comment.dto.CommentResponseDto;
import com.sparta.eduwithme.domain.comment.dto.CommentRoomDto;
import com.sparta.eduwithme.domain.comment.entity.Comment;
import com.sparta.eduwithme.domain.question.entity.Question;
import com.sparta.eduwithme.domain.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByQuestion(Question question, Pageable pageable);
    Page<Comment> findAllByUser(User user, Pageable pageable);

    @Query("SELECT new com.sparta.eduwithme.domain.comment.dto.CommentRoomDto(" +
            "c.id, c.user.nickName, c.comment, c.createdAt, c.updatedAt, r.roomName) " +
            "FROM Comment c " +
            "JOIN c.question q " +
            "JOIN q.room r " +
            "JOIN c.user u " +
            "WHERE c.user.id = :userId")
    Page<CommentRoomDto> findCommentsWithRoomByUserId(@Param("userId") Long userId, Pageable pageable);
}
