package com.AurumPro.dtos.cliente.pj;

public record PessoaJuridicaDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String cnpj,
        String cep,
        String uf,
        String localidade,
        String bairro,
        String logradouro
) {
}
