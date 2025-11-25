package com.AurumPro.dtos.componentes.custo;

import java.math.BigDecimal;

public record CreateCustoDTO(
        Long empresaId,
        String nome,
        BigDecimal valor
) {
}
