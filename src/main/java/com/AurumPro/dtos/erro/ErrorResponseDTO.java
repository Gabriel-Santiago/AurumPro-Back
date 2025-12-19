package com.AurumPro.dtos.erro;

public record ErrorResponseDTO(
        boolean success,
        String message
) {
}
