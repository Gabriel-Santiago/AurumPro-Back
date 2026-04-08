package com.AurumPro.entities.componentes;

import com.AurumPro.entities.base.BaseComponenteEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "microServico")
public class MicroServico extends BaseComponenteEntity {

    private String descricao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "servicoId")
    private Servico servico;

    public MicroServico() {
    }

    public MicroServico(String descricao,
                        Servico servico) {
        this.descricao = descricao;
        this.servico = servico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }
}
