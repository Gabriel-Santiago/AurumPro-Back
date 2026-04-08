package com.AurumPro.entities.empresa;

import com.AurumPro.entities.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "colaborador")
public class Colaborador extends BaseEntity {

    private String telefone;

    private String funcao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;

    public Colaborador() {
    }

    public Colaborador(String telefone,
                       String funcao,
                       Empresa empresa) {
        this.telefone = telefone;
        this.funcao = funcao;
        this.empresa = empresa;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
