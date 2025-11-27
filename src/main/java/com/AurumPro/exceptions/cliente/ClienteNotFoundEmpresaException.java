package com.AurumPro.exceptions.cliente;

public class ClienteNotFoundEmpresaException extends RuntimeException {

    public ClienteNotFoundEmpresaException(){
        super("Cliente não está associado(a) a Empresa");
    }
}
