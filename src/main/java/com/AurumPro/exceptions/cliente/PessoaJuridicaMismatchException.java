package com.AurumPro.exceptions.cliente;

public class PessoaJuridicaMismatchException extends RuntimeException {

    public PessoaJuridicaMismatchException(){
        super("O ID não pertence a uma Pessoa Jurídica");
    }
}
