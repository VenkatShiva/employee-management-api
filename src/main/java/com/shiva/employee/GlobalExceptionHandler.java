package com.shiva.employee;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import tools.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> payloadValidation(MethodArgumentNotValidException exception) {
        Map<String, String> errMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errMap.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> payloadConversion(HttpMessageNotReadableException exception) {

        Map<String, String> errMap = new HashMap<>();
        Throwable cause = exception.getMostSpecificCause();

        if (cause instanceof InvalidFormatException invalidFormatException) {
            invalidFormatException.getPath()
                    .stream()
                    .forEach(err -> {
                        errMap.put(err.getPropertyName(), "Invalid value");
                    });
            return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);
        }

        errMap.put("error", "Invalid payload");
        return new ResponseEntity<>(errMap, HttpStatus.BAD_REQUEST);
    }

}
