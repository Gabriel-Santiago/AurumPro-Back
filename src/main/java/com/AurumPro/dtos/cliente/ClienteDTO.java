package com.AurumPro.dtos.cliente;

import com.AurumPro.enums.TipoPessoa;

public record ClienteDTO (
        Long id,
        String nome,
        String email,
        String telefone,
        Integer idade,
        String cnpj,
        String cep,
        String uf,
        String localidade,
        String bairro,
        String logradouro,
        TipoPessoa tipoPessoa
){
}
