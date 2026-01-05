package com.AurumPro.utils;

import com.AurumPro.exceptions.utils.CnpjEmpresaExistException;
import com.AurumPro.repositories.empresa.EmpresaRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidateEmpresaCnpjExist {

    private final EmpresaRepository repository;

    public ValidateEmpresaCnpjExist(EmpresaRepository repository) {
        this.repository = repository;
    }

    public void validate(String cnpj) {
        if (repository.existsByCnpj(cnpj)) {
            throw new CnpjEmpresaExistException();
        }
    }
}
