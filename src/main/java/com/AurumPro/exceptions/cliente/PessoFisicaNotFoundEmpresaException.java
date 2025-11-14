package com.AurumPro.exceptions.cliente;

public class PessoFisicaNotFoundEmpresaException extends RuntimeException {

    public PessoFisicaNotFoundEmpresaException(){
        super("Pessoa não está associada a Empresa");
    }
}
