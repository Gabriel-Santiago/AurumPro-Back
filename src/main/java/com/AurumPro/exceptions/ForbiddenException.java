package com.AurumPro.exceptions;

public abstract class ForbiddenException extends RuntimeException{
    protected ForbiddenException(String message) {
        super(message);
    }
}
