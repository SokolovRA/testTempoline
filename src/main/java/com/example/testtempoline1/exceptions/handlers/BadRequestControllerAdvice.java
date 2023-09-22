package com.example.testtempoline1.exceptions.handlers;

import com.example.testtempoline1.exceptions.IncorrectArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BadRequestControllerAdvice {
    @ExceptionHandler(IncorrectArgumentException.class)
    public ResponseEntity<String> handleIncorrectArgumentException(IncorrectArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
