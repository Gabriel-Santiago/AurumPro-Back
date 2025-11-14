package com.AurumPro.dtos.cliente.pf;

public record DeletePessoaFisicaDTO(
        Long empresaId,
        Long id,
        String senha
) {
}
