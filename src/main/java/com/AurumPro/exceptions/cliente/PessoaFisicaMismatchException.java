package com.AurumPro.exceptions.cliente;

public class PessoaFisicaMismatchException extends RuntimeException {

    public PessoaFisicaMismatchException(){
        super("O ID não pertence a uma Pessoa Física");
    }
}
