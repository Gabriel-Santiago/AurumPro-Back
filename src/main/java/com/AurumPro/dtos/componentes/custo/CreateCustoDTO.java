package com.AurumPro.dtos.componentes.custo;

import java.math.BigDecimal;

public record CreateCustoDTO(
        Long empresaId,
        Long propostaId,
        String nome,
        BigDecimal valor
) {
}
