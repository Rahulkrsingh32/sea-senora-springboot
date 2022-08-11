package com.backend.seasenora.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String desc) {
        super(desc);
    }

}
