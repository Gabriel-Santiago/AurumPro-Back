package com.AurumPro.utils;

import com.AurumPro.exceptions.endereco.CepIsEmptyException;
import com.AurumPro.exceptions.endereco.CepNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ValidateCep {

    public void validate(String cep){
        if(cep == null){
            throw new CepNotFoundException();
        }

        if(cep.trim().isEmpty()){
            throw new CepIsEmptyException();
        }
    }
}
