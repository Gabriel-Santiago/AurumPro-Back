package com.AurumPro.dtos.cliente;

import com.AurumPro.enums.TipoPessoa;

public record FindTipoPessoabyUfDTO(
        TipoPessoa tipoPessoa,
        String uf
){
}
