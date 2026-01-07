package com.AurumPro.dtos.cliente;

import com.AurumPro.enums.TipoPessoa;

public record DeleteClienteDTO (
        Long id,
        String senha,
        TipoPessoa tipoPessoa
){
}
