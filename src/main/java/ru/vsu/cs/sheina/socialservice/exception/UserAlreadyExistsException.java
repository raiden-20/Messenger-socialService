package ru.vsu.cs.sheina.socialservice.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends AppException{
    public UserAlreadyExistsException() {
        super("User already exists", HttpStatus.CONFLICT);
    }
}
