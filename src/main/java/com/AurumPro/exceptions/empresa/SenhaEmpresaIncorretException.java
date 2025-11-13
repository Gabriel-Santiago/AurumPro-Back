package com.AurumPro.exceptions.empresa;

public class SenhaEmpresaIncorretException extends RuntimeException{

    public SenhaEmpresaIncorretException(){
        super("A senha está incorreta!");
    }
}
