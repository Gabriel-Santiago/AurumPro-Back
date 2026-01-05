package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.ConflictException;

public class ConvenioNomeExistException extends ConflictException {

    public ConvenioNomeExistException(){
        super("Nome do convênio já existente para empresa!");
    }
}
