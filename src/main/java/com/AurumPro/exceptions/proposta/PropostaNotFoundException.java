package com.AurumPro.exceptions.proposta;

import com.AurumPro.exceptions.NotFoundException;

public class PropostaNotFoundException extends NotFoundException {

    public PropostaNotFoundException(){
        super("ID da Proposta não foi encontrada!");
    }
}
