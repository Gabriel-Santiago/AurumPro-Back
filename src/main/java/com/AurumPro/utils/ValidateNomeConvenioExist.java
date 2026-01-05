package com.AurumPro.utils;

import com.AurumPro.exceptions.utils.ConvenioNomeExistException;
import com.AurumPro.repositories.componentes.ConvenioRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidateNomeConvenioExist {

    private final ConvenioRepository repository;

    public ValidateNomeConvenioExist(ConvenioRepository repository) {
        this.repository = repository;
    }

    public void validate(String nome, Long empresaId) {
        if (repository.existsByNomeAndEmpresaId(nome, empresaId)) {
            throw new ConvenioNomeExistException();
        }
    }
}
