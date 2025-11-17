package com.AurumPro.dtos.proposta;

import com.AurumPro.entities.componentes.MicroServico;
import com.AurumPro.entities.componentes.Servico;

import java.math.BigDecimal;
import java.util.List;

public record ItemPropostaDTO(
        List<Servico> servico,
        List<MicroServico> microServico,
        BigDecimal valorTotal
) {
}
