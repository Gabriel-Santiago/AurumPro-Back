package com.AurumPro.dtos.componentes.microServico;

public record CreateMicroServicoDTO(
        Long id,
        Long servicoId,
        String nome,
        String descricao
) {
}
