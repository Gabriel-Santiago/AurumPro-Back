package com.AurumPro.repositories.empresa;

import com.AurumPro.entities.empresa.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Boolean existsByCnpj(String cnpj);

    Optional<Empresa> findByEmailAndSenha(String email, String senha);
}
