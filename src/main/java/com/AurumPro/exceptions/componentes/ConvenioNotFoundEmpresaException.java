package com.AurumPro.exceptions.componentes;

public class ConvenioNotFoundEmpresaException extends RuntimeException {

    public ConvenioNotFoundEmpresaException(){
        super("Convênio não está associado a empresa!");
    }
}
