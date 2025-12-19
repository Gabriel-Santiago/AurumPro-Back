package com.AurumPro.exceptions.cliente;

import com.AurumPro.exceptions.ForbiddenException;

public class ClienteNotAssociatedToEmpresaException extends ForbiddenException {

    public ClienteNotAssociatedToEmpresaException(){
        super("Cliente não está associado(a) a Empresa");
    }
}
