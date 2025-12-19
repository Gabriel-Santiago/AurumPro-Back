package com.AurumPro.exceptions;

public abstract class UnauthorizedException extends RuntimeException{
    protected UnauthorizedException(String message) {
        super(message);
    }
}
