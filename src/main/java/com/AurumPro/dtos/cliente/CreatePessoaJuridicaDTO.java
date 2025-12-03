package com.AurumPro.dtos.cliente;

public record CreatePessoaJuridicaDTO(
        Long id,
        String responsavel,
        String email,
        String numero,
        String cnpj
) {
}
