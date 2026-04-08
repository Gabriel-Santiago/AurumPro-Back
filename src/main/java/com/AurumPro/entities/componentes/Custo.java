package com.AurumPro.entities.componentes;

import com.AurumPro.entities.base.BaseComponenteEntity;
import com.AurumPro.entities.proposta.Proposta;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "custo")
public class Custo extends BaseComponenteEntity {

    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "proposta_id")
    private Proposta proposta;

    public Custo() {
    }

    public Custo(BigDecimal valor,
                 Proposta proposta) {
        this.valor = valor;
        this.proposta = proposta;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public void setProposta(Proposta proposta) {
        this.proposta = proposta;
    }
}
