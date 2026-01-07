package com.AurumPro.dtos.cliente;

public record CreatePessoaJuridicaDTO(
        String responsavel,
        String email,
        String numero,
        String cnpj
) {
}
