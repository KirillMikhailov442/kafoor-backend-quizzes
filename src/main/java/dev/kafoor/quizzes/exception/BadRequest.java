package dev.kafoor.quizzes.exception;

import org.springframework.http.HttpStatus;

public class BadRequest extends BaseException {
    public BadRequest(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}