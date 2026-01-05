package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.ConflictException;

public class MicroServicoNomeExistException extends ConflictException {

    public MicroServicoNomeExistException(){
        super("Nome do micro serviço já existente para empresa e serviço!");
    }
}
