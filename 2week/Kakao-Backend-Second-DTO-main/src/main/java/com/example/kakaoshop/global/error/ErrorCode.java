package com.example.kakaoshop.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /* 400 BAD_REQUEST : 잘못된 요청 */
    TOKEN_MISS(BAD_REQUEST, "토큰이 없습니다."),
    WRONG_EMAIL_TYPE(BAD_REQUEST, "잘못된 이메일 형식입니다."),
    WRONG_PASSWORD_TYPE(BAD_REQUEST, "영문, 숫자, 특수문자가 포함되어야 하고 공백이 포함될 수 없습니다.:password"),
    WRONG_PASSWORD_LENGTH(BAD_REQUEST, "8에서 20자 이내여야 합니다.:password"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "계정 정보가 존재하지 않습니다"),
    WRONG_PASSWORD(UNAUTHORIZED, "비밀번호가 틀렸습니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    QUESTION_NOT_FOUND(NOT_FOUND, "존재하지 않는 카테고리 입니다."),
    IMAGE_NOT_FOUND(NOT_FOUND, "해당 이미지를 찾을 수 없습니다"),
    MEMBER_NOT_FOUND(NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_MEMBER_EMAIL(CONFLICT, "존재하는 이메일 입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String detail;
}