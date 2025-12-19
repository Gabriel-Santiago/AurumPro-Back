package com.AurumPro.exceptions.utils;

import com.AurumPro.exceptions.BadRequestException;

public class DataNascimentoIsNull extends BadRequestException {

    public DataNascimentoIsNull(){
        super("Data de Nascimento não pode ser nulo!");
    }
}
