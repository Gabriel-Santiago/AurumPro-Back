package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.NotFoundException;

public class CpfNotFoundException extends NotFoundException {

    public CpfNotFoundException(){
        super("CPF não foi encontrado!");
    }
}
