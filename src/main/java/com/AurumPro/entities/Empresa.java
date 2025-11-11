package com.AurumPro.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empresa")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long empresaId;

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
    private Integer numero;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Proposta> propostaList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cliente> clienteList = new ArrayList<>();
}
