package com.AurumPro.enums;

import lombok.Getter;

@Getter
public enum TipoConsultor {

    CONSULTOR("Consultor"),
    INSTRUTOR("Instrutor"),
    PALESTRANTE("Palestrante");

    private String descricao;

    TipoConsultor(String descricao){
        this.descricao = descricao;
    }
}
