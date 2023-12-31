package com.vt.carparkingservice.exception;

public class InvalidCarNumberException extends RuntimeException{
    public InvalidCarNumberException(String message) {
        super(message);
    }
}
