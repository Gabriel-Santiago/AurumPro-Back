package com.AurumPro.dtos.proposta;

import com.AurumPro.dtos.cliente.ClienteDTO;
import com.AurumPro.dtos.empresa.ColaboradorDTO;
import com.AurumPro.dtos.empresa.EmpresaDTO;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.util.List;

public record PropostaDocumentoDTO(
        Long id,
        BigDecimal valorTotal,
        ClienteDTO cliente,
        EmpresaDTO empresa,
        @Nullable ColaboradorDTO colaborador,
        List<Long> servicoList,
        List<Long> microServicoDTOList,
        List<Long> custoList
) {
}
