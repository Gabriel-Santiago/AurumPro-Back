package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.NotFoundException;

public class CnpjNotFoundException extends NotFoundException {

    public CnpjNotFoundException(){
        super("CNPJ não foi encontrado!");
    }
}
