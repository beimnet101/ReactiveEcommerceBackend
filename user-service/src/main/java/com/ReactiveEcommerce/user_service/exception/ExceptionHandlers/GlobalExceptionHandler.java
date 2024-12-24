package com.ReactiveEcommerce.user_service.exception.ExceptionHandlers;

import com.ReactiveEcommerce.user_service.exception.CredentialException;
import com.ReactiveEcommerce.user_service.exception.UserAlreadyExistException;
import com.ReactiveEcommerce.user_service.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.ReactiveEcommerce.user_service.exception.ExceptionHandlers.BusinessErrorCode.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends Exception{

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleException(RuntimeException exception){
        return ResponseEntity
            .status(INTERNAL_SERVER_ERROR)
            .body(
                ExceptionResponse.builder()
                    .error(exception.getMessage())
                    .build()
            );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleException(IllegalArgumentException exception){
        return ResponseEntity
            .status(INTERNAL_SERVER_ERROR)
            .body(
                ExceptionResponse.builder()
                    .error(exception.getMessage())
                    .build()
            );
    }



    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleException(UserNotFoundException exception){
        return ResponseEntity
            .status(NOT_FOUND)
            .body(
                ExceptionResponse.builder()
                    .businessErrorCode(USER_NOT_FOUND.getCode())
                    .businessErrorDescription(USER_NOT_FOUND.getDescription())
                    .error(exception.getMessage())
                    .build()
            );
    }



    @ExceptionHandler(CredentialException.class)
    public ResponseEntity<ExceptionResponse> handleException(CredentialException exception){
        return ResponseEntity
            .status(UNAUTHORIZED)
            .body(
                ExceptionResponse.builder()
                    .businessErrorCode(USER_PASSWORD_INCORRECT.getCode())
                    .businessErrorDescription(USER_PASSWORD_INCORRECT.getDescription())
                    .error(exception.getMessage())
                    .build()
            );
    }
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ExceptionResponse> handleException(UserAlreadyExistException exception){
        return ResponseEntity
            .status(CONFLICT)
            .body(
                ExceptionResponse.builder()
                    .businessErrorCode(USER_ALREADY_EXISTS.getCode())
                    .businessErrorDescription(USER_EMAIL_ALREADY_EXISTS.getDescription())
                    .error(exception.getMessage())
                    .build()
            );
    }

}
