package com.AurumPro.repositories.proposta;

import com.AurumPro.entities.proposta.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {

    List<Proposta> findByEmpresaId(Long empresaId);

    List<Proposta> findByEmpresaIdAndClienteId(Long empresaId, Long clienteId);
}
