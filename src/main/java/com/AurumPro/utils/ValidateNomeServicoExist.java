package com.AurumPro.utils;

import com.AurumPro.exceptions.utils.ServicoNomeExistException;
import com.AurumPro.repositories.componentes.ServicoRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidateNomeServicoExist {

    private final ServicoRepository repository;

    public ValidateNomeServicoExist(ServicoRepository repository) {
        this.repository = repository;
    }

    public void validate(String nome, Long empresaId) {
        if (repository.existsByNomeAndEmpresaId(nome, empresaId)) {
            throw new ServicoNomeExistException();
        }
    }
}
