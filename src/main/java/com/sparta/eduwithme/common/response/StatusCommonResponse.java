package com.sparta.eduwithme.common.response;

import lombok.Getter;

@Getter
public class StatusCommonResponse {

    private final Integer httpStatusCode;
    private final String message;
    private final String photoUrl;

    // 세 가지 필드를 사용하는 생성자
    public StatusCommonResponse(int httpStatusCode, String message, String photoUrl) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.photoUrl = photoUrl;
    }

    // 두 가지 필드만 사용하는 생성자
    public StatusCommonResponse(int httpStatusCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.message = message;
        this.photoUrl = null; // 파일 URL을 null로 초기화
    }
}