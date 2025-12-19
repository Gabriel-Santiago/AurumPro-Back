package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.BadRequestException;

public class CpfIsEmptyException extends BadRequestException {

    public CpfIsEmptyException(){
        super("CPF não pode estar vazio!");
    }
}
