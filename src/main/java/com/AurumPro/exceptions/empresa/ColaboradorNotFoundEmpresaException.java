package com.AurumPro.exceptions.empresa;

public class ColaboradorNotFoundEmpresaException extends RuntimeException {

    public ColaboradorNotFoundEmpresaException(){
        super("Consultor não está associado a Empresa");
    }
}
