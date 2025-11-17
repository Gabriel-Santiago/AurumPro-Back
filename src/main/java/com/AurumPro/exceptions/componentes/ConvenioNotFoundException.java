package com.AurumPro.exceptions.componentes;

public class ConvenioNotFoundException extends RuntimeException {

    public ConvenioNotFoundException(){
        super("Convênio não foi encontrado!");
    }
}
