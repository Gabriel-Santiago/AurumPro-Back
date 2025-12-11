package com.AurumPro.exceptions.proposta;

public class StatusInvalidoException extends RuntimeException{

    public StatusInvalidoException(String statusAtual, String novoStatus){
        super("Não é possível mudar de " + statusAtual + " para " + novoStatus);
    }
}
