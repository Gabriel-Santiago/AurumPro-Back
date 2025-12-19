package com.AurumPro.exceptions.proposta;

import com.AurumPro.exceptions.UnprocessableEntityException;

public class StatusInvalidoException extends UnprocessableEntityException {

    public StatusInvalidoException(String statusAtual, String novoStatus){
        super("Não é possível mudar de " + statusAtual + " para " + novoStatus);
    }
}
