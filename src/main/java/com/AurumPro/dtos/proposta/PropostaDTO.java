package com.AurumPro.dtos.proposta;

import com.AurumPro.dtos.componentes.custo.CustoDTO;
import com.AurumPro.enums.TipoDesconto;

import java.math.BigDecimal;
import java.util.List;

public record PropostaDTO(
        Long id,
        Long clienteId,
        String nomeCliente,
        Long convenioId,
        Long colaboradorId,
        Long empresaId,
        String nomeConvenio,
        List<CustoDTO> custoList,
        List<ItemPropostaDTO> itemPropostaList,
        TipoDesconto tipoDesconto,
        boolean desconto,
        BigDecimal valorDesconto,
        BigDecimal porcentagemDesconto,
        BigDecimal valorTotal
) {
}
