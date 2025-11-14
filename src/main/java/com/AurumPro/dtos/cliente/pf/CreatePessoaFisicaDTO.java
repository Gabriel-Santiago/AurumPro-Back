package com.AurumPro.dtos.cliente.pf;

import java.time.LocalDate;

public record CreatePessoaFisicaDTO(
        Long id,
        String nome,
        String email,
        String telefone,
        String cep,
        String numero,
        LocalDate dataNascimento,
        String cpf
) {
}
