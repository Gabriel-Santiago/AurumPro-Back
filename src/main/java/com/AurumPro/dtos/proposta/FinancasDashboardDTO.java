package com.AurumPro.dtos.proposta;

import java.math.BigDecimal;
import java.util.List;

public record FinancasDashboardDTO (
        List<PropostaDTO> emAvaliacao,
        List<PropostaDTO> aceita,
        List<PropostaDTO> recusada,
        List<PropostaDTO> expirada,
        BigDecimal valorEmAvaliacao,
        BigDecimal valorRecebido,
        Long totalPropostas,
        Long  propostasEmAvaliacao,
        Long propostasAceitas,
        Long propostasRecusadas,
        Long propostasExpiradas,
        BigDecimal taxaAceitacao
){
}
