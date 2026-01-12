package com.AurumPro.repositories.proposta;

import com.AurumPro.entities.proposta.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtividadeRepository extends JpaRepository<Atividade, Long> {

    List<Atividade> findByProposta(Long propostaId);

    boolean existsByNomeAndPropostaId(String nome, Long propostaId);
}
