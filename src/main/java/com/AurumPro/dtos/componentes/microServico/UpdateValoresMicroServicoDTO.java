package com.AurumPro.dtos.componentes.microServico;

import java.math.BigDecimal;

public record UpdateValoresMicroServicoDTO(
        Long empresaId,
        Long id,
        BigDecimal valorHora,
        BigDecimal qtdHora
) {
}
