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
    EMAIL_MISMATCH(HttpStatus.NOT_FOUND, "이메일이 일치하지 않습니다."),
    USER_NOT_UNIQUE(HttpStatus.BAD_REQUEST, "중복된 유저가 존재합니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "유효하지 않은 비밀번호 형식입니다."),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "현재 비밀번호가 틀렸습니다."),

    // photo
    FILE_DIRECTORY_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 디렉토리 생성에 실패했습니다."),
    FILE_STORAGE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장에 실패했습니다."),

    // room
    SAME_NEW_ROOM_NAME(HttpStatus.BAD_REQUEST, "동일한 이름의 방을 생성할 수 없습니다."),
    CAN_NOT_MADE_ROOM(HttpStatus.BAD_REQUEST, "방을 2개 이상 만들 수 없습니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "방을 찾을 수 없습니다."),
    ROOM_NOT_OWNER(HttpStatus.NOT_FOUND, "방의 주인이 아닙니다."),

    // question
    KEYWORD_NOT_FOUND(HttpStatus.NOT_FOUND,"키워드를 찾을 수 없습니다."),
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND,"해당 문제를 찾을 수 없습니다."),
    QUESTION_ROOM_MISMATCH(HttpStatus.NOT_FOUND,"해당 문제가 선택한 방에 속하지 않습니다.");


    // comment

    // chat

    private final HttpStatus status;
    private final String message;
}
