package com.sparta.eduwithme.domain.user.dto;

import com.sparta.eduwithme.domain.user.entity.User;

public class UserDto {
    private Long id;
    private String nickName;

    public UserDto(User user) {
        this.id = user.getId();
        this.nickName = user.getNickName();
    }

    // getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}