package com.AurumPro.dtos.empresa;

public record CreateEmpresaDTO(
        String cnpj,
        String senha,
        String responsavel,
        String inscricaoMunicipal
) {
}
