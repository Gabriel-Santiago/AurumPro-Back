package com.AurumPro.exceptions.endereco;

public class CepNotFoundException extends RuntimeException{

    public CepNotFoundException(){
        super("Cep não foi encontrado!");
    }
}
