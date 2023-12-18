package ru.vsu.cs.sheina.socialservice.exception;

import org.springframework.http.HttpStatus;

public class RelationAlreadyExistException extends AppException{
    public RelationAlreadyExistException() {
        super("Relation already exists", HttpStatus.CONFLICT);
    }
}
