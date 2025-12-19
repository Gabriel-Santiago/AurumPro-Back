package com.AurumPro.exceptions;

public abstract class ConflictException extends RuntimeException{
    protected ConflictException(String message) {
        super(message);
    }
}
