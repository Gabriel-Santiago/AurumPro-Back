package com.AurumPro.entities.componentes;

import com.AurumPro.entities.base.BaseComponenteEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "microServico")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class MicroServico extends BaseComponenteEntity {

    private BigDecimal valorHora;
    private BigDecimal qtdHora;
    private BigDecimal valorTotal;

    @ManyToOne(optional = false)
    @JoinColumn(name = "servicoId")
    private Servico servico;
}
