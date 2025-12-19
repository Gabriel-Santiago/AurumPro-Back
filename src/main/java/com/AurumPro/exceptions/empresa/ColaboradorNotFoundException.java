package com.AurumPro.exceptions.empresa;

import com.AurumPro.exceptions.NotFoundException;

public class ColaboradorNotFoundException extends NotFoundException {

    public ColaboradorNotFoundException(){
        super("ID do consultor não foi encontrado!");
    }
}
