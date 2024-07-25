package com.sparta.eduwithme.domain.question.entity;

import com.sparta.eduwithme.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "Learning_status")
public class LearningStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    public LearningStatus(Question question, User user, QuestionType questionType) {
        this.question = question;
        this.user = user;
        this.questionType = questionType;
    }

    public enum QuestionType {
        SOLVE,
        WRONG
    }
}
