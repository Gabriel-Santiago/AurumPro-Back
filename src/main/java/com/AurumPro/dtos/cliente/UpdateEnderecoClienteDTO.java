package com.AurumPro.dtos.cliente;

import com.AurumPro.enums.TipoPessoa;

public record UpdateEnderecoClienteDTO(
        Long id,
        String cep,
        String numero,
        TipoPessoa tipoPessoa
) {
}
