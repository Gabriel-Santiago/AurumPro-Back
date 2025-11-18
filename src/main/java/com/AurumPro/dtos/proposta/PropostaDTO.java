package com.AurumPro.dtos.proposta;

import com.AurumPro.entities.componentes.Custo;
import com.AurumPro.entities.proposta.ItemProposta;
import com.AurumPro.enums.TipoDesconto;

import java.math.BigDecimal;
import java.util.List;

public record PropostaDTO(
        Long clienteId,
        String nomeCliente,
        Long convenioId,
        String nomeConvenio,
        List<Custo> custoList,
        List<ItemProposta> itemPropostaList,
        TipoDesconto tipoDesconto,
        boolean desconto,
        BigDecimal valorDesconto,
        BigDecimal porcentagemDesconto,
        BigDecimal valorTotal
) {
}
