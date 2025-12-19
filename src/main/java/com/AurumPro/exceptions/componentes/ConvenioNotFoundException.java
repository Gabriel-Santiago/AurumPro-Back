package com.AurumPro.exceptions.componentes;

import com.AurumPro.exceptions.NotFoundException;

public class ConvenioNotFoundException extends NotFoundException {

    public ConvenioNotFoundException(){
        super("Convênio não foi encontrado!");
    }
}
