package com.AurumPro.dtos.cliente.pf;

public record PessoaFisicaDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        Integer idade,
        String cep,
        String uf,
        String localidade,
        String bairro,
        String logradouro
) {
}
