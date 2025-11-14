package com.AurumPro.exceptions.utils;

public class CpfNotFoundException extends RuntimeException{

    public CpfNotFoundException(){
        super("CPF não foi encontrado!");
    }
}
