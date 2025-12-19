package com.AurumPro.exceptions.endereco;

import com.AurumPro.exceptions.NotFoundException;

public class CepNotFoundException extends NotFoundException {

    public CepNotFoundException(){
        super("Cep não foi encontrado!");
    }
}
