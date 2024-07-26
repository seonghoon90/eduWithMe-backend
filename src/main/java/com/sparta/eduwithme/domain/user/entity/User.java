package com.sparta.eduwithme.domain.user.entity;

import com.sparta.eduwithme.common.TimeStamp;
import com.sparta.eduwithme.domain.question.entity.LearningStatus;
import com.sparta.eduwithme.domain.question.entity.QuestionType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String nickName;

    @Column
    private String ranking;

    @Column
    private String photoUrl;

    @Column
    private String refreshToken;

    private Long kakaoId;


    public User(String email, String password, String nickName, String ranking, String photoUrl) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.ranking = ranking;
        this.photoUrl = photoUrl;
    }

    public User(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }

    @Builder
    public User(String email, String encodedPassword, String nickName, Long kakaoId) {
        this.email = email;
        this.password = encodedPassword;
        this.nickName = nickName;
        this.kakaoId = kakaoId;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateNickname(String newNickname) {
        this.nickName = newNickname;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void updatePhotoUrl(String newPhotoUrl) {
        this.photoUrl = newPhotoUrl;
    }

    @OneToMany(mappedBy = "user")
    private List<LearningStatus> learningStatusList = new ArrayList<>();

    public Long getTotalPoints() {
        return learningStatusList.stream()
                .filter(ls -> ls.getQuestionType() == QuestionType.SOLVE) // 해결된 문제만 계산
                .mapToLong(ls -> ls.getQuestion().getPoint()) // 문제의 포인트를 합산
                .sum();
    }
}
