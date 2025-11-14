package com.AurumPro.utils;

import com.AurumPro.exceptions.utils.DataNascimentoIsNull;

import java.time.LocalDate;
import java.time.Period;

public class CalculateAge {

    public int idade(LocalDate dataNascimento){
        if (dataNascimento == null) {
            throw new DataNascimentoIsNull();
        }

        LocalDate hoje = LocalDate.now();
        return Period.between(dataNascimento, hoje).getYears();
    }
}
