package com.AurumPro.exceptions.utils;

public class CnpjNotFoundException extends RuntimeException{

    public CnpjNotFoundException(){
        super("CNPJ não foi encontrado!");
    }
}
