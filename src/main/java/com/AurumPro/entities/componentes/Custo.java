package com.AurumPro.entities.componentes;

import com.AurumPro.entities.base.BaseComponenteEntity;
import com.AurumPro.entities.proposta.Proposta;
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
@Table(name = "custo")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Custo extends BaseComponenteEntity {

    private BigDecimal valor;

    @ManyToOne
    @JoinColumn(name = "proposta_id")
    private Proposta proposta;
}
