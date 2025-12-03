package com.AurumPro.dtos.empresa;

import com.AurumPro.enums.TipoConsultor;

public record CreateConsultorDTO(
        Long empresaId,
        String nome,
        String telefone,
        TipoConsultor tipoConsultor
) {
}
