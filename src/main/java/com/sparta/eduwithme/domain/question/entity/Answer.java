package com.sparta.eduwithme.domain.question.entity;

import com.sparta.eduwithme.common.TimeStamp;
import com.sparta.eduwithme.domain.question.dto.AnswerRequestDto;
import com.sparta.eduwithme.domain.question.dto.AnswerUpdateRequestDto;
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

    @OneToOne(mappedBy = "answer")
    private Question question;

    public Answer(String first, String second, String third, String fourth, int answered) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
        this.answered = answered;
    }

    public void updateAnswer(AnswerUpdateRequestDto answerUpdateRequestDto) {
        this.first = answerUpdateRequestDto.getFirst();
        this.second = answerUpdateRequestDto.getSecond();
        this.third = answerUpdateRequestDto.getThird();
        this.fourth = answerUpdateRequestDto.getFourth();
        this.answered = answerUpdateRequestDto.getAnswered();
    }
    protected void initQuestion(Question question) {
        this.question = question;
    }
}
