package com.AurumPro.utils;

import com.AurumPro.exceptions.utils.MicroServicoNomeExistException;
import com.AurumPro.repositories.componentes.MicroServicoRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidateNomeMicroServicoExist {

    private final MicroServicoRepository repository;

    public ValidateNomeMicroServicoExist(MicroServicoRepository repository) {
        this.repository = repository;
    }

    public void validate(String nome, Long empresaId, Long servicoId) {
        if (repository.existsByNomeAndEmpresaIdAndServicoId(nome, empresaId, servicoId)) {
            throw new MicroServicoNomeExistException();
        }
    }
}
