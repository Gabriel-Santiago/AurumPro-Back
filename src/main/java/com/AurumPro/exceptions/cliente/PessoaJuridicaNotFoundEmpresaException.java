package com.AurumPro.exceptions.cliente;

public class PessoaJuridicaNotFoundEmpresaException extends RuntimeException {

    public PessoaJuridicaNotFoundEmpresaException(){
        super("Pessoa Jurídica não está associada a Empresa");
    }
}
