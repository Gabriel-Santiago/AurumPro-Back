package com.AurumPro.dtos.proposta;

import com.AurumPro.enums.StatusProposta;

public record UpdateStatusPropostaDTO(
        Long propostaId,
        StatusProposta statusProposta
) {
}
