package com.AurumPro.dtos.componentes.custo;

import java.math.BigDecimal;

public record CustoDTO(
        Long id,
        String nome,
        BigDecimal valor
) {
}
