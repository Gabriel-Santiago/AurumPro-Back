package com.AurumPro.repositories.empresa;

import com.AurumPro.entities.empresa.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Boolean existsByCnpj(String cnpj);
}
