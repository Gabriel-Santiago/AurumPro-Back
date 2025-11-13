package com.AurumPro.exceptions.empresa;

public class CnpjExistException extends RuntimeException{

    public CnpjExistException(){
        super("CNPJ já existente!");
    }
}
