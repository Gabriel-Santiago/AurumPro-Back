package com.AurumPro.repositories.componentes;

import com.AurumPro.entities.componentes.Custo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustoRepository extends JpaRepository<Custo, Long> {

    @Query("select c.id from Custo c where c.proposta.propostaId = :propostaId")
    List<Long> findByPropostaId(Long propostaId);
}
