package com.AurumPro.entities.cliente;

import com.AurumPro.entities.base.BaseEntity;
import com.AurumPro.entities.empresa.Empresa;
import com.AurumPro.enums.TipoPessoa;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "cliente")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class Cliente extends BaseEntity {

    private String email;

    private String telefone;

    private String cep;

    private String rua;

    private String bairro;

    private String cidade;

    private String estado;

    private String numero;

    @Enumerated(EnumType.STRING)
    private TipoPessoa tipoPessoa;

    private LocalDate dataNascimento;
    private Integer idade;
    private String cpf;

    private String cnpj;

    @ManyToOne(optional = false)
    @JoinColumn(name = "empresaId")
    private Empresa empresa;
}
