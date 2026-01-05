package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.ConflictException;

public class CnpjEmpresaExistException extends ConflictException {

    public CnpjEmpresaExistException(){
        super("CNPJ já existente para empresa!");
    }
}
