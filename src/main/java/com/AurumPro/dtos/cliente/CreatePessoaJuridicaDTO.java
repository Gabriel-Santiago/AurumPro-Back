package com.AurumPro.dtos.cliente;

public record CreatePessoaJuridicaDTO(
        Long id,
        String nome,
        String email,
        String numero,
        String cnpj
) {
}
