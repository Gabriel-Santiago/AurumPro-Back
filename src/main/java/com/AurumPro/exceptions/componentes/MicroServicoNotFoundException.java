package com.AurumPro.exceptions.componentes;

import com.AurumPro.exceptions.NotFoundException;

public class MicroServicoNotFoundException extends NotFoundException {

    public MicroServicoNotFoundException(){
        super("MicroServico não foi encontrado");
    }
}
