package com.AurumPro.exceptions.cliente;

public class ClienteNotFoundException extends RuntimeException {

    public ClienteNotFoundException(){
        super("Pessoa não foi encontrada");
    }
}
