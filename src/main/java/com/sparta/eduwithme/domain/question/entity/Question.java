package com.sparta.eduwithme.domain.question.entity;

import com.sparta.eduwithme.common.TimeStamp;
import com.sparta.eduwithme.domain.question.dto.QuestionRequestDTO;
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

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    public Question(Room room, QuestionRequestDTO requestDTO) {
        this.room = room;
        this.title = requestDTO.getTitle();
        this.content = requestDTO.getContent();
        this.category = requestDTO.getCategory();
        this.difficulty = requestDTO.getDifficulty();
        this.point = requestDTO.getPoint();
        this.answers = new ArrayList<>();
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
        answer.setQuestion(this);
    }


}
