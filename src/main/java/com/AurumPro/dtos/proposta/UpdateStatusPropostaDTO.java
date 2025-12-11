package com.AurumPro.dtos.proposta;

import com.AurumPro.enums.StatusProposta;

public record UpdateStatusPropostaDTO(
        Long empresaId,
        Long propostaId,
        StatusProposta statusProposta
) {
}
