package com.diary.diary.exception.diary;

public class InvalidLockException extends RuntimeException {
    public InvalidLockException(String message) {
        super(message);
    }
}
