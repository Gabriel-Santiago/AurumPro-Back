package com.AurumPro.entities.proposta;

import com.AurumPro.entities.componentes.MicroServico;
import com.AurumPro.entities.componentes.Servico;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "itemProposta")
public class ItemProposta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemPropostaId;

    private BigDecimal valorHora;
    private BigDecimal qtdHora;
    private BigDecimal valorTotal;

    @ManyToOne
    @JoinColumn(name = "propostaId")
    private Proposta proposta;

    @ManyToOne
    @JoinColumn(name = "servicoId")
    private Servico servico;

    @ManyToOne
    @JoinColumn(name = "microServicoId")
    private MicroServico microServico;

    public ItemProposta() {
    }

    public ItemProposta(long itemPropostaId,
                        BigDecimal valorHora,
                        BigDecimal qtdHora,
                        BigDecimal valorTotal,
                        Proposta proposta,
                        Servico servico,
                        MicroServico microServico) {
        this.itemPropostaId = itemPropostaId;
        this.valorHora = valorHora;
        this.qtdHora = qtdHora;
        this.valorTotal = valorTotal;
        this.proposta = proposta;
        this.servico = servico;
        this.microServico = microServico;
    }

    public long getItemPropostaId() {
        return itemPropostaId;
    }

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }

    public BigDecimal getQtdHora() {
        return qtdHora;
    }

    public void setQtdHora(BigDecimal qtdHora) {
        this.qtdHora = qtdHora;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public void setProposta(Proposta proposta) {
        this.proposta = proposta;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public MicroServico getMicroServico() {
        return microServico;
    }

    public void setMicroServico(MicroServico microServico) {
        this.microServico = microServico;
    }
}
