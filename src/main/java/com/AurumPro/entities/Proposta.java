package com.AurumPro.entities;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemProposta> itemPropostaList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "empresaId")
    private Empresa empresa;
}
