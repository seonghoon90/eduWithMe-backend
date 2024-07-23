package com.sparta.eduwithme.domain.profile.entity;

import com.sparta.eduwithme.common.TimeStamp;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "user")
public class User extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column
    private String profileImage;

    @Column(nullable = false)
    private String userRank;

    @Column(nullable = false)
    private String password;

    @ElementCollection
    @CollectionTable(name = "previous_passwords", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "password")
    private List<String> previousPasswords = new LinkedList<>();

    public void updateNickname(String newNickname) {
        this.nickname = newNickname;
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