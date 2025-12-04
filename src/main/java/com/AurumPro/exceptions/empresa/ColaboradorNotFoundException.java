package com.AurumPro.exceptions.empresa;

public class ColaboradorNotFoundException extends RuntimeException{

    public ColaboradorNotFoundException(){
        super("ID do consultor não foi encontrado!");
    }
}
