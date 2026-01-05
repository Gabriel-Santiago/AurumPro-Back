package com.AurumPro.repositories.componentes;

import com.AurumPro.entities.componentes.MicroServico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MicroServicoRepository extends JpaRepository<MicroServico, Long> {

    List<MicroServico> findByServicoId(Long servicoId);

    boolean existsByNomeAndEmpresaIdAndServicoId(String nome, Long empresaId, Long servicoId);
}
