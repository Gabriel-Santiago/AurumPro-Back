package com.AurumPro.dtos.proposta;

import java.util.List;

public record CreateItemPropostaDTO(
        Long servicoId,
        List<Long> microServicoId
) {
}
