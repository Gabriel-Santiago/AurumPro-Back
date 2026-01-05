package com.AurumPro.utils;

import com.AurumPro.exceptions.utils.CnpjClienteExistException;
import com.AurumPro.repositories.cliente.ClienteRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidateClienteCnpjExist {

    private final ClienteRepository repository;

    public ValidateClienteCnpjExist(ClienteRepository repository) {
        this.repository = repository;
    }

    public void validate(String cnpj) {
        if (repository.existsByCnpj(cnpj)) {
            throw new CnpjClienteExistException();
        }
    }
}
