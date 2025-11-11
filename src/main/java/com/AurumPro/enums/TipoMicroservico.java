package com.AurumPro.enums;

import lombok.Getter;

@Getter
public enum TipoMicroservico {

    DIAGNOSTICO(TipoServico.CONSULTORIA, "Diagnóstico"),
    ASSESSORIA(TipoServico.CONSULTORIA, "Assessoria"),
    ANALISE(TipoServico.CONSULTORIA, "Análise de Resultados"),
    VISITA(TipoServico.CONSULTORIA, "Visita"),

    FINANCEIRA_M(TipoServico.MENTORIA, "Financeira"),
    FAMILIA_M(TipoServico.MENTORIA, "Familiar"),
    CONTROLE_FINANCEIRO_M(TipoServico.MENTORIA, "Controle Financeiro"),
    FORMACAO_DE_PRECO_M(TipoServico.MENTORIA, "Formação de Preço"),
    PLANEJAMENTO_M(TipoServico.MENTORIA, "Planejamento"),

    FINANCEIRA_W(TipoServico.WORKSHOP, "Financeira"),
    FAMILIA_W(TipoServico.WORKSHOP, "Familiar"),
    CONTROLE_FINANCEIRO_W(TipoServico.WORKSHOP, "Controle Financeiro"),
    FORMACAO_DE_PRECO_W(TipoServico.WORKSHOP, "Formação de Preço"),
    PLANEJAMENTO_W(TipoServico.WORKSHOP, "Planejamento"),

    PALESTRA(TipoServico.PALESTRA, "Palestra");

    private final TipoServico tipoServico;
    private final String descricao;

    TipoMicroservico(TipoServico tipoServico, String descricao){
        this.tipoServico = tipoServico;
        this.descricao = descricao;
    }
}
