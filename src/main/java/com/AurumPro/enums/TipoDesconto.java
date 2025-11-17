package com.AurumPro.enums;

import lombok.Getter;

@Getter
public enum TipoDesconto {

    PORCENTAGEM("Porcentagem"),
    VALOR("Valor"),
    NENHUM("Nenhum");

    private String descricao;

    TipoDesconto(String descricao){
        this.descricao = descricao;
    }
}
