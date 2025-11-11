package com.AurumPro.entities;

import com.AurumPro.enums.TipoMicroservico;
import com.AurumPro.enums.TipoServico;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

    @Enumerated(EnumType.STRING)
    private TipoServico tipoServico;

    @Enumerated(EnumType.STRING)
    private TipoMicroservico tipoMicroservico;

    private BigDecimal valorHora;
    private BigDecimal qtdHora;
    private BigDecimal valorTotal;

    @ManyToOne
    @JoinColumn(name = "propostaId")
    private Proposta proposta;

    @PrePersist
    @PreUpdate
    public void calcularValorTotal(){
        if(this.valorHora != null && this.qtdHora != null){
            this.valorTotal = this.valorHora.multiply(this.qtdHora);
        }else {
            this.valorTotal = BigDecimal.ZERO;
        }
    }
}
