package com.AurumPro.exceptions.empresa;

public class IdEmpresaNotFoundException extends RuntimeException{

    public IdEmpresaNotFoundException(){
        super("Empresa não foi encontrada!");
    }
}
