package com.example.springbootkafka.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity handleCustomException(final CustomException ex) {
        return new ResponseEntity<>("Something went wrong !", HttpStatus.I_AM_A_TEAPOT);
    }

}
