package com.AurumPro.dtos.componentes.microServico;

public record CreateMicroServicoDTO(
        Long servicoId,
        String nome,
        String descricao
) {
}
