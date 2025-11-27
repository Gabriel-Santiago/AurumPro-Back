package com.AurumPro.dtos.cliente;

import com.AurumPro.enums.TipoPessoa;

public record FindTipoPessoabyUfDTO(
        Long id,
        TipoPessoa tipoPessoa,
        String uf
){
}
