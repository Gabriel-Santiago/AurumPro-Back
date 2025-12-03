package com.AurumPro.dtos.empresa;

import com.AurumPro.enums.TipoConsultor;

public record ConsultorDTO(
        Long id,
        String nome,
        String telefone,
        TipoConsultor tipoConsultor
) {
}
