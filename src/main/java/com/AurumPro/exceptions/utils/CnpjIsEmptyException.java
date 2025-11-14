package com.AurumPro.exceptions.utils;

public class CnpjIsEmptyException extends RuntimeException{

    public CnpjIsEmptyException(){
        super("CNPJ não pode estar vazio!");
    }
}
