package com.AurumPro.exceptions.empresa;

public class ConsultorNotFoundEmpresaException extends RuntimeException {

    public ConsultorNotFoundEmpresaException(){
        super("Consultor não está associado a Empresa");
    }
}
