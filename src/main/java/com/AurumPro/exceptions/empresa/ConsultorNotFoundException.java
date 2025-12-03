package com.AurumPro.exceptions.empresa;

public class ConsultorNotFoundException extends RuntimeException{

    public ConsultorNotFoundException(){
        super("ID do consultor não foi encontrado!");
    }
}
