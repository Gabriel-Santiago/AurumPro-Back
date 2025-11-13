package com.AurumPro.dtos.empresa;

public record UpdateCepEmpresaDTO(
        Long id,
        String cep,
        String numero
) {
}
