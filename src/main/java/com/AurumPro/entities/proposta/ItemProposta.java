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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "itemProposta")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class ItemProposta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long itemPropostaId;

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
}
