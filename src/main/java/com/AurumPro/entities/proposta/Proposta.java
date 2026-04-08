package com.AurumPro.entities.proposta;

import com.AurumPro.entities.cliente.Cliente;
import com.AurumPro.entities.componentes.Convenio;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.empresa.Colaborador;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.enums.StatusProposta;
import com.AurumPro.enums.TipoDesconto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "proposta")
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long propostaId;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataValidade;
    private LocalDateTime dataMudancaStatus;
    private BigDecimal valorTotal;
    private boolean desconto;
    private BigDecimal valorDesconto;
    private BigDecimal porcentagemDesconto;

    @Enumerated(EnumType.STRING)
    private TipoDesconto tipoDesconto;

    @Enumerated(EnumType.STRING)
    private StatusProposta statusProposta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clienteId")
    private Cliente cliente;

    @OneToOne(optional = false)
    @JoinColumn(name = "convenioId")
    private Convenio convenio;

    @OneToMany(mappedBy = "proposta", cascade = CascadeType.ALL)
    private List<Custo> custoList;

    @OneToMany(mappedBy = "proposta", cascade = CascadeType.ALL)
    private List<ItemProposta> itemPropostaList;

    @ManyToOne
    @JoinColumn(name = "colaboradorId")
    private Colaborador colaborador;

    @OneToMany(mappedBy = "proposta", cascade = CascadeType.ALL)
    private List<Atividade> atividadeList;

    public Proposta() {
    }

    public Proposta(Long propostaId,
                    LocalDateTime dataCriacao,
                    LocalDateTime dataValidade,
                    LocalDateTime dataMudancaStatus,
                    BigDecimal valorTotal,
                    boolean desconto,
                    BigDecimal valorDesconto,
                    BigDecimal porcentagemDesconto,
                    TipoDesconto tipoDesconto,
                    StatusProposta statusProposta,
                    Empresa empresa,
                    Cliente cliente,
                    Convenio convenio,
                    List<Custo> custoList,
                    List<ItemProposta> itemPropostaList,
                    Colaborador colaborador,
                    List<Atividade> atividadeList) {
        this.propostaId = propostaId;
        this.dataCriacao = dataCriacao;
        this.dataValidade = dataValidade;
        this.dataMudancaStatus = dataMudancaStatus;
        this.valorTotal = valorTotal;
        this.desconto = desconto;
        this.valorDesconto = valorDesconto;
        this.porcentagemDesconto = porcentagemDesconto;
        this.tipoDesconto = tipoDesconto;
        this.statusProposta = statusProposta;
        this.empresa = empresa;
        this.cliente = cliente;
        this.convenio = convenio;
        this.custoList = custoList;
        this.itemPropostaList = itemPropostaList;
        this.colaborador = colaborador;
        this.atividadeList = atividadeList;
    }

    public Long getPropostaId() {
        return propostaId;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataValidade() {
        return dataValidade;
    }

    public LocalDateTime getDataMudancaStatus() {
        return dataMudancaStatus;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public boolean isDesconto() {
        return desconto;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public BigDecimal getPorcentagemDesconto() {
        return porcentagemDesconto;
    }

    public TipoDesconto getTipoDesconto() {
        return tipoDesconto;
    }

    public StatusProposta getStatusProposta() {
        return statusProposta;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public List<Custo> getCustoList() {
        return custoList;
    }

    public List<ItemProposta> getItemPropostaList() {
        return itemPropostaList;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public List<Atividade> getAtividadeList() {
        return atividadeList;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataValidade(LocalDateTime dataValidade) {
        this.dataValidade = dataValidade;
    }

    public void setDataMudancaStatus(LocalDateTime dataMudancaStatus) {
        this.dataMudancaStatus = dataMudancaStatus;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setDesconto(boolean desconto) {
        this.desconto = desconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public void setPorcentagemDesconto(BigDecimal porcentagemDesconto) {
        this.porcentagemDesconto = porcentagemDesconto;
    }

    public void setTipoDesconto(TipoDesconto tipoDesconto) {
        this.tipoDesconto = tipoDesconto;
    }

    public void setStatusProposta(StatusProposta statusProposta) {
        this.statusProposta = statusProposta;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setConvenio(Convenio convenio) {
        this.convenio = convenio;
    }

    public void setCustoList(List<Custo> custoList) {
        this.custoList = custoList;
    }

    public void setItemPropostaList(List<ItemProposta> itemPropostaList) {
        this.itemPropostaList = itemPropostaList;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public void setAtividadeList(List<Atividade> atividadeList) {
        this.atividadeList = atividadeList;
    }
}
