package com.AurumPro.entities.cliente;

import com.AurumPro.entities.base.ClienteEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "pessoaFisica")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class PessoaFisica extends ClienteEntity {

    private LocalDate dataNascimento;
    private Integer idade;
    private String cpf;
}
