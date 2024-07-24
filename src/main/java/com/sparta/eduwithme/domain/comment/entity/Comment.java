package com.sparta.eduwithme.domain.comment.entity;

import com.sparta.eduwithme.common.TimeStamp;
import com.sparta.eduwithme.domain.comment.dto.CommentRequestDto;
import com.sparta.eduwithme.domain.question.entity.Question;
import com.sparta.eduwithme.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    public Comment(CommentRequestDto commentRequestDto, Question question, User user) {
        this.comment = commentRequestDto.getComment();
        this.question = question;
        this.user = user;
    }

    public String getUserNickname() {
        return this.user.getNickName();
    }

}
