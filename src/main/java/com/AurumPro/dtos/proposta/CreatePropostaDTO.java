package com.AurumPro.dtos.proposta;

import com.AurumPro.enums.TipoDesconto;

import java.math.BigDecimal;
import java.util.List;

public record CreatePropostaDTO(
        Long empresaId,
        Long clienteId,
        Long convenioId,
        Long colaboradorId,
        List<Long> custoList,
        List<Long> itemPropostaList,
        TipoDesconto tipoDesconto,
        boolean desconto,
        BigDecimal valorDesconto,
        BigDecimal porcentagemDesconto
) {
}
