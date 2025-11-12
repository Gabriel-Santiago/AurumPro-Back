package com.AurumPro.apis;

public record Endereco (
        String cep,
        String logradouro,
        String bairro,
        String localidade,
        String uf
){
}
