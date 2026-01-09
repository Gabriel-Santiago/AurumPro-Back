package com.AurumPro.enums;

import lombok.Getter;

@Getter
public enum TipoPessoa {

    PF("Pessoa Física"),
    PJ("Pessoa Jurídica");

    private final String descricao;

    TipoPessoa(String descricao){
        this.descricao = descricao;
    }
}
