package com.AurumPro.repositories.componentes;

import com.AurumPro.entities.componentes.Convenio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConvenioRepository extends JpaRepository<Convenio, Long> {

    List<Convenio> findByEmpresaId(Long empresaId);

    boolean existsByNomeAndEmpresaId(String nome, Long empresaId);
}
