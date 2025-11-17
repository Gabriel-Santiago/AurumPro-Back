package com.AurumPro.dtos.componentes.microServico;

import java.math.BigDecimal;

public record CreateMicroServicoDTO(
        Long id,
        Long servicoId,
        String nome,
        BigDecimal valorHora,
        BigDecimal qtdHora
) {
}
