package com.AurumPro.entities.cliente;

import com.AurumPro.entities.base.BaseClienteEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pessoaJuridica")
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Setter
public class PessoaJuridica extends BaseClienteEntity {

    private String cnpj;
}
