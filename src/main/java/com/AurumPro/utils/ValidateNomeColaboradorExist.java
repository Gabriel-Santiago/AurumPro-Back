package com.AurumPro.utils;

import com.AurumPro.exceptions.utils.ColaboradorNomeExistException;
import com.AurumPro.repositories.empresa.ColaboradorRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidateNomeColaboradorExist {

    private final ColaboradorRepository repository;

    public ValidateNomeColaboradorExist(ColaboradorRepository repository) {
        this.repository = repository;
    }

    public void validate(String nome, Long empresaId) {
        if (repository.existsByNomeAndEmpresaId(nome, empresaId)) {
            throw new ColaboradorNomeExistException();
        }
    }
}
