package com.AurumPro.utils;

import com.AurumPro.exceptions.utils.CnpjIsEmptyException;
import com.AurumPro.exceptions.utils.CnpjNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ValidateCnpj {

    public void validate(String cnpj){
        if(cnpj == null){
            throw new CnpjNotFoundException();
        }

        if(cnpj.trim().isEmpty()){
            throw new CnpjIsEmptyException();
        }
    }
}
