package com.AurumPro.dtos.proposta;

import java.math.BigDecimal;

public record CreateItemPropostaDTO(
        Long servicoId,
        Long microServicoId,
        BigDecimal valorHora,
        BigDecimal quantidadeHoras,
        BigDecimal valorTotal
) {
}
