package com.AurumPro.exceptions.empresa;

import com.AurumPro.exceptions.UnauthorizedException;

public class SenhaEmpresaIncorretException extends UnauthorizedException {

    public SenhaEmpresaIncorretException(){
        super("A senha está incorreta!");
    }
}
