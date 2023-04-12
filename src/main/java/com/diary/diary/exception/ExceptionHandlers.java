package com.diary.diary.exception;

import com.diary.diary.dto.response.ErrorResponse;
import com.diary.diary.exception.diary.InvalidLockException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

public class ExceptionHandlers {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> invalidLockHandler(InvalidLockException exception,
                                                            HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                request.getRequestURI(),
                exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> NotFoundHandler(NotFoundException exception,
                                                         HttpServletRequest request){
        ErrorResponse error = new ErrorResponse(
                ZonedDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                request.getRequestURI(),
                exception.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
