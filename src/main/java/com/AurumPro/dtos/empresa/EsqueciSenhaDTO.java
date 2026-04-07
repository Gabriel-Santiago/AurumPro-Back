package com.AurumPro.dtos.empresa;

public record EsqueciSenhaDTO(
        String email,
        String senha,
        String codigoAcesso
) {
}
