package com.AurumPro.exceptions;

public abstract class UnprocessableEntityException extends RuntimeException{
    protected UnprocessableEntityException(String message) {
        super(message);
    }
}
