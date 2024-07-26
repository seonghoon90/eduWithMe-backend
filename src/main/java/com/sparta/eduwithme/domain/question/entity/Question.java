package com.sparta.eduwithme.domain.question.entity;

import com.sparta.eduwithme.common.TimeStamp;
import com.sparta.eduwithme.domain.comment.entity.Comment;
import com.sparta.eduwithme.domain.question.dto.QuestionRequestDto;
import com.sparta.eduwithme.domain.question.dto.QuestionUpdateRequestDto;
import com.sparta.eduwithme.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "questions")
public class Question extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @Column
    private Long point;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LearningStatus> learningStatusList = new ArrayList<>();


    public Question(Room room, String title, String content, Category category, Difficulty difficulty, Long point, Answer answer) {
        this.room = room;
        this.title = title;
        this.content = content;
        this.category = category;
        this.difficulty = difficulty;
        this.point = point;
        this.answer = answer;
        if (answer != null) {
            answer.initQuestion(this);
        }
    }

    public void updateQuestion(QuestionUpdateRequestDto questionUpdateRequestDto, Long updatePoint) {
        this.title = questionUpdateRequestDto.getTitle();
        this.content = questionUpdateRequestDto.getContent();
        this.category = questionUpdateRequestDto.getCategory();
        this.difficulty = questionUpdateRequestDto.getDifficulty();
        this.point = questionUpdateRequestDto.getPoint();
    }
}
