package com.AurumPro.dtos.empresa;

public record EmpresaDTO(
        Long empresaId,
        String email,
        String nome,
        String cnpj,
        String inscricaoMunicipal,
        String responsavel,
        String telefone,
        String cep,
        String rua,
        String bairro,
        String cidade,
        String estado,
        String numero
) {
}
