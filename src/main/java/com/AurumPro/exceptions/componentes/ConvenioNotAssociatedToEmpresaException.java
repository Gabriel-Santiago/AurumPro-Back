package com.AurumPro.exceptions.componentes;

import com.AurumPro.exceptions.ForbiddenException;

public class ConvenioNotAssociatedToEmpresaException extends ForbiddenException {

    public ConvenioNotAssociatedToEmpresaException(){
        super("Convênio não está associado a empresa!");
    }
}
