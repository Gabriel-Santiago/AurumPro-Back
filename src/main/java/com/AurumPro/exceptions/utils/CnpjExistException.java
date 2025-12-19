package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.ConflictException;

public class CnpjExistException extends ConflictException {

    public CnpjExistException(){
        super("CNPJ já existente!");
    }
}
