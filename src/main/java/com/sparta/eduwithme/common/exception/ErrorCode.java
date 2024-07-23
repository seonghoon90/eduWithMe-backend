package com.sparta.eduwithme.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // 공통 오류 코드
    FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "실패했습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),
    TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "토큰을 찾을 수 없습니다."),

    // user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 유저를 찾을 수 없습니다."),

    // room
    SAME_NEW_ROOM_NAME(HttpStatus.BAD_REQUEST, "동일한 이름의 방을 생성할 수 없습니다."),
    CAN_NOT_MADE_ROOM(HttpStatus.BAD_REQUEST, "방을 2개 이상 만들 수 없습니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "방을 찾을 수 없습니다.");

    // question

    // comment

    // chat

    private final HttpStatus status;
    private final String message;
}
