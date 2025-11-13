package com.AurumPro.entities.empresa;

import com.AurumPro.entities.base.BaseEntity;
import com.AurumPro.entities.cliente.PessoaFisica;
import com.AurumPro.entities.cliente.PessoaJuridica;
import com.AurumPro.entities.componentes.Convenio;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.componentes.MicroServico;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.proposta.Proposta;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "empresa")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Empresa extends BaseEntity{

    private String email;
    private String senha;

    private String cnpj;
    private String inscricaoMunicipal;
    private String responsavel;
    private String telefone;

    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private String numero;

    @OneToMany(mappedBy = "empresa")
    private List<PessoaFisica> pessoaFisicaList;

    @OneToMany(mappedBy = "empresa")
    private List<PessoaJuridica> pessoaJuridicaList;

    @OneToMany(mappedBy = "empresa")
    private List<Servico> servicos;

    @OneToMany(mappedBy = "empresa")
    private List<MicroServico> microservicos;

    @OneToMany(mappedBy = "empresa")
    private List<Convenio> convenios;

    @OneToMany(mappedBy = "empresa")
    private List<Custo> custos;

    @OneToMany(mappedBy = "empresa")
    private List<Proposta> propostas;
}
