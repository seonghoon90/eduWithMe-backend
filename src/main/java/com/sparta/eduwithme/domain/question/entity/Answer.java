package com.sparta.eduwithme.domain.question.entity;

import com.sparta.eduwithme.common.TimeStamp;
import com.sparta.eduwithme.domain.question.dto.AnswerRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "answers")
public class Answer extends TimeStamp {
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

    public Answer(AnswerRequestDto answerRequestDto) {
        this.first = answerRequestDto.getFirst();
        this.second = answerRequestDto.getSecond();
        this.third = answerRequestDto.getThird();
        this.fourth = answerRequestDto.getFourth();
        this.answered = answerRequestDto.getAnswered();
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}
