package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.ConflictException;

public class AtividadeNomeExistException extends ConflictException {

    public AtividadeNomeExistException(){
        super("Nome da atividade já existente para proposta!");
    }
}
