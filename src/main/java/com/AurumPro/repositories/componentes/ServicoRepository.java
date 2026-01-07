package com.AurumPro.repositories.componentes;

import com.AurumPro.entities.componentes.Servico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    boolean existsByNomeAndEmpresaId(String nome, Long empresaId);

    List<Servico> findByEmpresaId(Long empresaId);
}
