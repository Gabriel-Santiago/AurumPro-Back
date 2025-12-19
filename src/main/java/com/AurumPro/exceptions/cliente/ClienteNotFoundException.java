package com.AurumPro.exceptions.cliente;

import com.AurumPro.exceptions.NotFoundException;

public class ClienteNotFoundException extends NotFoundException {

    public ClienteNotFoundException(){
        super("Pessoa não foi encontrada");
    }
}
