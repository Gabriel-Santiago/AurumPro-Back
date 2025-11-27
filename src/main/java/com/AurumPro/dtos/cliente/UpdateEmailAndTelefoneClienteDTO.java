package com.AurumPro.dtos.cliente;

import com.AurumPro.enums.TipoPessoa;

public record UpdateEmailAndTelefoneClienteDTO(
        Long id,
        String email,
        String telefone,
        TipoPessoa tipoPessoa
) {
}
