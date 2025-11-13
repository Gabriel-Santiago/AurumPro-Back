package com.AurumPro.exceptions.endereco;

public class CepIsEmptyException extends RuntimeException{

    public CepIsEmptyException(){
        super("Cep não pode estar vazio!");
    }
}
