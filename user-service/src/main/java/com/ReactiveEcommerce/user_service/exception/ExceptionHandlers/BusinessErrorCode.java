package com.ReactiveEcommerce.user_service.exception.ExceptionHandlers;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum BusinessErrorCode {
    DENIED(403, FORBIDDEN, "Access Denied"),
    UNAVAILABLE(404, NOT_FOUND, "OTP doesn't exist"),
    ILLEGAL(401, UNAUTHORIZED, "User is not authorized"),
    NOTIFICATION_NOT_SENT(403, FORBIDDEN, "OTP is not sent"),
    TOKEN_EXPIRED(500, INTERNAL_SERVER_ERROR, "OTP is expired"),
    USER_NOT_FOUND(404, NOT_FOUND, "User not found"),
    USER_ALREADY_EXISTS(409, CONFLICT, "User already exists"),
    USER_PASSWORD_INCORRECT(401, UNAUTHORIZED, "Incorrect credentials"),
    //    USER_ALREADY_EXIST(400, BAD_REQUEST, "User already exists"),
    USER_EMAIL_ALREADY_EXISTS(409, CONFLICT, "Email already exists")
    ;

    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCode(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
