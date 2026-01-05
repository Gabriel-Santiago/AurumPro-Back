package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.ConflictException;

public class ColaboradorNomeExistException extends ConflictException {

    public ColaboradorNomeExistException(){
        super("Nome do colaborador já existente para empresa!");
    }
}
