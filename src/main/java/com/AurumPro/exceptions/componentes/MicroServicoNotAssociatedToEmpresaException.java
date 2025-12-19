package com.AurumPro.exceptions.componentes;

import com.AurumPro.exceptions.ForbiddenException;

public class MicroServicoNotAssociatedToEmpresaException extends ForbiddenException {

    public MicroServicoNotAssociatedToEmpresaException(){
        super("MicroServico não está associada a Empresa e Serviço");
    }
}
