package com.backend.seasenora.exceptions;

public class JWTTokenMalformedException extends RuntimeException {
    public JWTTokenMalformedException(String message) {
        super(message);
    }

}
