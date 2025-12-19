package com.AurumPro.exceptions.cliente;

import com.AurumPro.exceptions.ForbiddenException;

public class PessoaJuridicaNotAssociatedToEmpresaException extends ForbiddenException {

    public PessoaJuridicaNotAssociatedToEmpresaException(){
        super("Pessoa Jurídica não está associada a Empresa");
    }
}
