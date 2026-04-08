package com.AurumPro.entities.cliente;

import com.AurumPro.entities.base.BaseEntity;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.entities.proposta.Proposta;
import com.AurumPro.enums.TipoPessoa;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente extends BaseEntity {

    private String email;

    private String telefone;

    private String cep;

    private String rua;

    private String bairro;

    private String cidade;

    private String estado;

    private String numero;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    private LocalDate dataNascimento;
    private Integer idade;
    private String cpf;

    private String cnpj;
    private String responsavel;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proposta> propostas;

    public Cliente() {
    }

    public Cliente(String email,
                   String telefone,
                   String cep,
                   String rua,
                   String bairro,
                   String cidade,
                   String estado,
                   String numero,
                   TipoPessoa tipoPessoa,
                   LocalDate dataNascimento,
                   Integer idade,
                   String cpf,
                   String cnpj,
                   String responsavel,
                   Empresa empresa,
                   List<Proposta> propostas) {
        this.email = email;
        this.telefone = telefone;
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.numero = numero;
        this.tipoPessoa = tipoPessoa;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.responsavel = responsavel;
        this.empresa = empresa;
        this.propostas = propostas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public TipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public List<Proposta> getPropostas() {
        return propostas;
    }

    public void setPropostas(List<Proposta> propostas) {
        this.propostas = propostas;
    }
}
