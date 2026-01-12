package com.AurumPro.exceptions.proposta;

import com.AurumPro.exceptions.NotFoundException;

public class AtividadeNotFoundException extends NotFoundException {

    public AtividadeNotFoundException(){
        super("ID da atividade não foi encontrada!");
    }
}
