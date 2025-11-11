package com.AurumPro.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(name = "clienteId")
@Table(name = "pessoaJuridica")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class PessoaJuridica extends Cliente {

    private String cnpj;
}
