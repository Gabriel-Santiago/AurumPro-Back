package com.AurumPro.dtos.cliente.pj;

public record CreatePessoaJuridicaDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String cep,
        String numero,
        String cnpj
) {
}
