package com.AurumPro.exceptions.proposta;

public class PropostaNotFoundException extends RuntimeException{

    public PropostaNotFoundException(){
        super("ID da Proposta não foi encontrada!");
    }
}
