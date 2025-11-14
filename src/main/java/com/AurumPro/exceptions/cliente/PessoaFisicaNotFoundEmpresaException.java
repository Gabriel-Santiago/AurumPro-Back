package com.AurumPro.exceptions.cliente;

public class PessoaFisicaNotFoundEmpresaException extends RuntimeException {

    public PessoaFisicaNotFoundEmpresaException(){
        super("Pessoa Física não está associada a Empresa");
    }
}
