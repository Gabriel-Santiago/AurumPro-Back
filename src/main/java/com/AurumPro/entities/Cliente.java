package com.AurumPro.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "cliente")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long clienteId;

    private String nome;
    private String email;
    private String telefone;

    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String estado;
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "empresaId")
    private Empresa empresa;
}
