package com.AurumPro.entities.proposta;

import com.AurumPro.entities.empresa.Empresa;
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

@Entity
@Table(name = "atividade")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long atividadeId;

    private String nome;
    private boolean concluida;

    @ManyToOne(optional = false)
    @JoinColumn(name = "propostaId")
    private Proposta proposta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;
}
