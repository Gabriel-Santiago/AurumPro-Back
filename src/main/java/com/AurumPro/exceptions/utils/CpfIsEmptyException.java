package com.AurumPro.exceptions.utils;

public class CpfIsEmptyException extends RuntimeException{

    public CpfIsEmptyException(){
        super("CPF não pode estar vazio!");
    }
}
