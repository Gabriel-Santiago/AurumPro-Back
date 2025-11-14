package com.AurumPro.dtos.cliente.pj;

public record DeletePessoaJuridicaDTO(
        Long empresaId,
        Long id,
        String senha
) {
}
