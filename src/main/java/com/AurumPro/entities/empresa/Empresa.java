package com.AurumPro.entities.empresa;

import com.AurumPro.entities.base.BaseEntity;
import com.AurumPro.entities.cliente.Cliente;
import com.AurumPro.entities.componentes.Convenio;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.componentes.MicroServico;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.proposta.Proposta;
import jakarta.persistence.CascadeType;
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

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cliente> clienteList;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Servico> servicoList;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MicroServico> microservicoList;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Convenio> convenioList;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Custo> custoList;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proposta> propostaList;

    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Consultor> consultorList;
}
