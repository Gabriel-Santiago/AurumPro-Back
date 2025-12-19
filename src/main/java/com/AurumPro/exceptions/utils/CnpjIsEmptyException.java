package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.BadRequestException;

public class CnpjIsEmptyException extends BadRequestException {

    public CnpjIsEmptyException(){
        super("CNPJ não pode estar vazio!");
    }
}
