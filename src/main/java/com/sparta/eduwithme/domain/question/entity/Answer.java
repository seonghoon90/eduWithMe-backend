package com.sparta.eduwithme.domain.question.entity;

import com.sparta.eduwithme.domain.question.dto.AnswerRequestDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String first;

    @Column(nullable = false)
    private String second;

    @Column(nullable = false)
    private String third;

    @Column(nullable = false)
    private String fourth;

    @Column(nullable = false)
    private int answered;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    public Answer(AnswerRequestDTO answerRequestDTO) {
        this.first = answerRequestDTO.getFirst();
        this.second = answerRequestDTO.getSecond();
        this.third = answerRequestDTO.getThird();
        this.fourth = answerRequestDTO.getFourth();
        this.answered = answerRequestDTO.getAnswered();
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
