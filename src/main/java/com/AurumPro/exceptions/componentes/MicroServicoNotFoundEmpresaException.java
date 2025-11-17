package com.AurumPro.exceptions.componentes;

public class MicroServicoNotFoundEmpresaException extends RuntimeException {

    public MicroServicoNotFoundEmpresaException(){
        super("MicroServico não está associada a Empresa e Serviço");
    }
}
