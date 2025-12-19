package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.NotFoundException;

public class IdNotFoundException extends NotFoundException {

    public IdNotFoundException(){
        super("ID não foi encontrado!");
    }
}
