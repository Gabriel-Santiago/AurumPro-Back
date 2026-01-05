package com.AurumPro.repositories.empresa;

import com.AurumPro.entities.empresa.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    boolean existsByNomeAndEmpresaId(String nome, Long empresaId);
}
