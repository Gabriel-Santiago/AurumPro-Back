package com.AurumPro.exceptions.componentes;

import com.AurumPro.exceptions.NotFoundException;

public class ServicoNotFoundException extends NotFoundException {

    public ServicoNotFoundException(){
        super("Servico não foi encontrado");
    }
}
