package com.AurumPro.repositories.proposta;

import com.AurumPro.entities.proposta.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    List<Atividade> findByPropostaPropostaId(Long propostaId);

    boolean existsByNomeAndPropostaPropostaId(String nome, Long propostaId);

    @Query("""
        select a from Atividade a
        join fetch a.proposta p
        join fetch p.empresa
        where a.atividadeId = :atividadeId
    """)
    Optional<Atividade> findAutorizada(@Param("atividadeId") Long atividadeId);

}
