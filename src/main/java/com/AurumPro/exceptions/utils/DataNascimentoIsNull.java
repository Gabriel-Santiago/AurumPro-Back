package com.AurumPro.exceptions.utils;

public class DataNascimentoIsNull extends RuntimeException{

    public DataNascimentoIsNull(){
        super("Data de Nascimento não pode ser nulo!");
    }
}
