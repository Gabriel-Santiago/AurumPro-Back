package com.AurumPro.entities.proposta;

import com.AurumPro.entities.cliente.Cliente;
import com.AurumPro.entities.componentes.Convenio;
import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.empresa.Empresa;
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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "proposta")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Proposta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long propostaId;

    private LocalDateTime dataCriacao;
    private BigDecimal valorTotal;
    private boolean desconto;
    private BigDecimal valorDesconto;
    private BigDecimal porcentagemDesconto;

    @Enumerated(EnumType.STRING)
    private TipoDesconto tipoDesconto;


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
}
