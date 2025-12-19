package com.AurumPro.exceptions.empresa;

import com.AurumPro.exceptions.NotFoundException;

public class EmpresaNotFoundException extends NotFoundException {

    public EmpresaNotFoundException(){
        super("ID da Empresa não foi encontrada!");
    }
}
