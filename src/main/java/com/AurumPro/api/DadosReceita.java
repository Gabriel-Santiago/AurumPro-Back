package com.AurumPro.apis;

public record DadosReceita (
        String nome,

        String cep,
        String logradouro,
        String bairro,
        String municipio,
        String uf,
        String numero,

        String email,
        String telefone
){
}
