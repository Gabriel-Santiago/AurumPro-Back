package com.AurumPro.exceptions.endereco;

import com.AurumPro.exceptions.BadRequestException;

public class CepIsEmptyException extends BadRequestException {

    public CepIsEmptyException(){
        super("Cep não pode estar vazio!");
    }
}
