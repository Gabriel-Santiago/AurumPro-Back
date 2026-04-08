package com.AurumPro.entities.proposta;

import com.AurumPro.entities.empresa.Empresa;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "atividade")
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long atividadeId;

    private String nome;
    private boolean concluida;

    @ManyToOne(optional = false)
    @JoinColumn(name = "propostaId")
    private Proposta proposta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;

    public Atividade() {
    }

    public Atividade(long atividadeId,
                     String nome,
                     boolean concluida,
                     Proposta proposta,
                     Empresa empresa) {
        this.atividadeId = atividadeId;
        this.nome = nome;
        this.concluida = concluida;
        this.proposta = proposta;
        this.empresa = empresa;
    }

    public long getAtividadeId() {
        return atividadeId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
