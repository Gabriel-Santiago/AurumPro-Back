package com.AurumPro.entities.empresa;

import com.AurumPro.entities.base.BaseEntity;
import com.AurumPro.entities.cliente.Cliente;
import com.AurumPro.entities.componentes.Convenio;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.componentes.MicroServico;
import com.AurumPro.entities.componentes.Servico;
import com.AurumPro.entities.proposta.Proposta;
import com.AurumPro.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "empresa")
public class Empresa extends BaseEntity implements UserDetails {

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

    private String codigoAcesso;
    private LocalDateTime expiracaoCodigo;
    private boolean podeAcessar;

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
    private List<Colaborador> colaboradorList;

    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_ADMIN;

    public Empresa() {
    }

    public Empresa(String email,
                   String senha,
                   String cnpj,
                   String inscricaoMunicipal,
                   String responsavel,
                   String telefone,
                   String cep,
                   String rua,
                   String bairro,
                   String cidade,
                   String estado,
                   String numero,
                   String codigoAcesso,
                   boolean podeAcessar,
                   List<Cliente> clienteList,
                   List<Servico> servicoList,
                   List<MicroServico> microservicoList,
                   List<Convenio> convenioList,
                   List<Custo> custoList,
                   List<Proposta> propostaList,
                   List<Colaborador> colaboradorList,
                   Role role) {
        this.email = email;
        this.senha = senha;
        this.cnpj = cnpj;
        this.inscricaoMunicipal = inscricaoMunicipal;
        this.responsavel = responsavel;
        this.telefone = telefone;
        this.cep = cep;
        this.rua = rua;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.numero = numero;
        this.codigoAcesso = codigoAcesso;
        this.podeAcessar = podeAcessar;
        this.clienteList = clienteList;
        this.servicoList = servicoList;
        this.microservicoList = microservicoList;
        this.convenioList = convenioList;
        this.custoList = custoList;
        this.propostaList = propostaList;
        this.colaboradorList = colaboradorList;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCep() {
        return cep;
    }

    public String getRua() {
        return rua;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getEstado() {
        return estado;
    }

    public String getNumero() {
        return numero;
    }

    public String getCodigoAcesso() {
        return codigoAcesso;
    }

    public boolean isPodeAcessar() {
        return podeAcessar;
    }

    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public List<Servico> getServicoList() {
        return servicoList;
    }

    public List<MicroServico> getMicroservicoList() {
        return microservicoList;
    }

    public List<Convenio> getConvenioList() {
        return convenioList;
    }

    public List<Custo> getCustoList() {
        return custoList;
    }

    public List<Proposta> getPropostaList() {
        return propostaList;
    }

    public List<Colaborador> getColaboradorList() {
        return colaboradorList;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getExpiracaoCodigo() {
        return expiracaoCodigo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCodigoAcesso(String codigoAcesso) {
        this.codigoAcesso = codigoAcesso;
    }

    public void setExpiracaoCodigo(LocalDateTime expiracaoCodigo) {
        this.expiracaoCodigo = expiracaoCodigo;
    }

    public void setPodeAcessar(boolean podeAcessar) {
        this.podeAcessar = podeAcessar;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    public void setServicoList(List<Servico> servicoList) {
        this.servicoList = servicoList;
    }

    public void setMicroservicoList(List<MicroServico> microservicoList) {
        this.microservicoList = microservicoList;
    }

    public void setConvenioList(List<Convenio> convenioList) {
        this.convenioList = convenioList;
    }

    public void setCustoList(List<Custo> custoList) {
        this.custoList = custoList;
    }

    public void setPropostaList(List<Proposta> propostaList) {
        this.propostaList = propostaList;
    }

    public void setColaboradorList(List<Colaborador> colaboradorList) {
        this.colaboradorList = colaboradorList;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
