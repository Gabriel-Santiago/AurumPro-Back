package com.AurumPro.dtos.empresa;

public record CreateColaboradorDTO(
        Long empresaId,
        String nome,
        String telefone,
        String funcao
) {
}
