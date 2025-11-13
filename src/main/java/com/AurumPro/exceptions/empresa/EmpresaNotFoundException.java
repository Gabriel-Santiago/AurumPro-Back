package com.AurumPro.exceptions.empresa;

public class EmpresaNotFoundException extends RuntimeException{

    public EmpresaNotFoundException(){
        super("ID da Empresa não foi encontrada!");
    }
}
