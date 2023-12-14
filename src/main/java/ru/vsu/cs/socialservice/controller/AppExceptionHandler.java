package ru.vsu.cs.socialservice.controller;

import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.vsu.cs.socialservice.exception.AppException;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handleException(AppException appException) {
        return ResponseEntity.status(appException.getStatus()).body(appException.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleJwtException(JWTDecodeException jwtDecodeException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad token");
    }
}
