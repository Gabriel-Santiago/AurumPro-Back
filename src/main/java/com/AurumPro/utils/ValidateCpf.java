package com.AurumPro.utils;

import com.AurumPro.exceptions.utils.CpfIsEmptyException;
import com.AurumPro.exceptions.utils.CpfNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ValidateCpf {

    public void validate(String cpf){
        if(cpf == null){
            throw new CpfNotFoundException();
        }

        if(cpf.trim().isEmpty()){
            throw new CpfIsEmptyException();
        }
    }
}
