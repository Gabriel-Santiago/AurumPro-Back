package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.ConflictException;

public class ServicoNomeExistException extends ConflictException {

    public ServicoNomeExistException(){
        super("Nome do serviço já existente para empresa!");
    }
}
