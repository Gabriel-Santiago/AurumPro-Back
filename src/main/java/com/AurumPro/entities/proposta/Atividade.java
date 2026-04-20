package com.AurumPro.entities.proposta;

import com.AurumPro.entities.base.BaseComponenteEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "atividade")
public class Atividade extends BaseComponenteEntity {

    private boolean concluida;

    @ManyToOne
    @JoinColumn(name = "proposta_id")
    private Proposta proposta;

    public Atividade() {
    }

    public Atividade(boolean concluida,
                     Proposta proposta) {
        this.concluida = concluida;
        this.proposta = proposta;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public void setProposta(Proposta proposta) {
        this.proposta = proposta;
    }
}
