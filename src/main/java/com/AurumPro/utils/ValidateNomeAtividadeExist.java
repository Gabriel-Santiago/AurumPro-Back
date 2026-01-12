package com.AurumPro.utils;

import com.AurumPro.exceptions.utils.AtividadeNomeExistException;
import com.AurumPro.repositories.proposta.AtividadeRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidateNomeAtividadeExist {

    private final AtividadeRepository repository;

    public ValidateNomeAtividadeExist(AtividadeRepository repository) {
        this.repository = repository;
    }

    public void validate(String nome, Long propostaId) {
        if (repository.existsByNomeAndPropostaId(nome, propostaId)) {
            throw new AtividadeNomeExistException();
        }
    }
}
