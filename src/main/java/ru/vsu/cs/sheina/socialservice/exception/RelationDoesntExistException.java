package ru.vsu.cs.sheina.socialservice.exception;

import org.springframework.http.HttpStatus;

public class RelationDoesntExistException extends AppException{
    public RelationDoesntExistException() {
        super("Relation doesn't exist", HttpStatus.BAD_REQUEST);
    }
}
