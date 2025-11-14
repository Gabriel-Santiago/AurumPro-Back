package com.AurumPro.exceptions.utils;

public class IdNotFoundException extends RuntimeException{

    public IdNotFoundException(){
        super("ID não foi encontrado!");
    }
}
