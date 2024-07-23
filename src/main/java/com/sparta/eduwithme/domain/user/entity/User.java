package com.sparta.eduwithme.domain.user.entity;

import com.sparta.eduwithme.common.TimeStamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="users")
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

    @ElementCollection
    @CollectionTable(name = "previous_passwords", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "password")
    private List<String> previousPasswords = new LinkedList<>();

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

    public void updateNickname(String newNickname) {
        this.nickName = newNickname;
    }

    public boolean checkPassword(String currentPassword) {
        return this.password.equals(currentPassword);
    }

    public boolean isPasswordRecentlyUsed(String newPassword) {
        return this.previousPasswords.contains(newPassword) || this.password.equals(newPassword);
    }

    public void updatePassword(String newPassword) {
        if (this.previousPasswords.size() >= 3) {
            this.previousPasswords.remove(0);
        }
        this.previousPasswords.add(this.password);
        this.password = newPassword;
    }
}
