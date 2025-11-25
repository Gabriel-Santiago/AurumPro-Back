package com.AurumPro.api;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCep {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/";

    public Endereco viaCep(String cep) throws Exception{
        RestTemplate restTemplate = new RestTemplate();
        String url = VIA_CEP_URL + cep + "/json";
        return restTemplate.getForObject(url, Endereco.class);
    }
}
