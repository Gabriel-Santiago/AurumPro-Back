package com.AurumPro.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "clienteId")
@Table(name = "pessoaFisica")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class PessoaFisica extends Cliente {

    private LocalDate dataNascimento;
    private Integer idade;
    private String cpf;
}
