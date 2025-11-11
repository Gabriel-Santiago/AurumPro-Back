package com.AurumPro.enums;

import lombok.Getter;

@Getter
public enum TipoServico {

    CONSULTORIA("Consultoria"),
    MENTORIA("Mentoria"),
    WORKSHOP("Workshop"),
    PALESTRA("Palestra");

    private String descricao;

    TipoServico(String descricao){
        this.descricao = descricao;
    }
}
