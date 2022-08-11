package com.backend.seasenora.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.backend.seasenora.exceptions.CustomerALLReadyExistException;
import com.backend.seasenora.exceptions.CustomerNotFoundException;
import com.backend.seasenora.model.ErrorResponse;

@RestControllerAdvice
public class CustomerExceptionHandler {

    final private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
            .getLogger(CustomerExceptionHandler.class);

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(CustomerNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDate.now());
        log.error(errorResponse);
        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(CustomerALLReadyExistException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(CustomerALLReadyExistException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDate.now());
        log.error(errorResponse);
        return ResponseEntity.ok(errorResponse);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(BadCredentialsException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value(),
                LocalDate.now());
        log.error(errorResponse);
        return ResponseEntity.ok(errorResponse);
    }

}
