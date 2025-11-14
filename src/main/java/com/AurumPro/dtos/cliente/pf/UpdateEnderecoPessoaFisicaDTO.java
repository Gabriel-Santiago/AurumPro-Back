package com.AurumPro.dtos.cliente.pf;

public record UpdateEnderecoPessoaFisicaDTO(
        Long id,
        String cep,
        String numero
) {
}
