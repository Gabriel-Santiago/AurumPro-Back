package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.ConflictException;

public class CnpjClienteExistException extends ConflictException {

    public CnpjClienteExistException(){
        super("CNPJ já existente para cliente!");
    }
}
