package com.AurumPro.exceptions.empresa;

import com.AurumPro.exceptions.ForbiddenException;

public class ColaboradorNotAssociatedToEmpresaException extends ForbiddenException {

    public ColaboradorNotAssociatedToEmpresaException(){
        super("Consultor não está associado a Empresa");
    }
}
