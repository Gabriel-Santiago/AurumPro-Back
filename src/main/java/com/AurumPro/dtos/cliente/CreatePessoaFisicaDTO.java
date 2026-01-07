package com.AurumPro.dtos.cliente;

import java.time.LocalDate;

public record CreatePessoaFisicaDTO(
        String nome,
        String email,
        String telefone,
        String cep,
        String numero,
        LocalDate dataNascimento,
        String cpf
) {
}
