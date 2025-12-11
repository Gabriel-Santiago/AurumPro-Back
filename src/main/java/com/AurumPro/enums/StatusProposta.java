package com.AurumPro.enums;

import lombok.Getter;

@Getter
public enum StatusProposta {

    EM_AVALIACAO("Em Avaliação"),
    ACEITA("Aceita"),
    RECUSADA("Recusada"),
    EXPIRADA("Expirada");

    private String descricao;

    StatusProposta(String descricao){
        this.descricao = descricao;
    }
}
