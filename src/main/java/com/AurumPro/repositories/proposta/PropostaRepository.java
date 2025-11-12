package com.AurumPro.repositories.proposta;

import com.AurumPro.entities.proposta.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {

}
