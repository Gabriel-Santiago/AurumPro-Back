package com.AurumPro.dtos.componentes.custo;

import java.math.BigDecimal;

public record CreateCustoDTO(
        String nome,
        BigDecimal valor
) {
}
